package com.demo.diplomaproject.domain.interactor

import com.demo.diplomaproject.core.global.scheduler.SchedulersProvider
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.model.data.storage.Prefs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val prefs: Prefs,
    private val authInteractor: AuthInteractor
) {

    private val db = Firebase.firestore

    fun getProfile(email: String): Completable =
        Completable.create { emitter ->
            db
                .collection(USERS_COLLECTION_NAME)
                .document(email)
                .get()
                .addOnSuccessListener { document ->
                    document?.let {
                        prefs.userProfile = it.toObject(UserProfile::class.java)
                    }
                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun updateUserProfile(userProfile: UserProfile): Completable =
        Completable
            .fromAction {
                db
                    .collection(USERS_COLLECTION_NAME)
                    .document(userProfile.email)
                    .set(userProfile)
                    .addOnSuccessListener {
                        prefs.userProfile = userProfile
                    }
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun updateHistory(testResult: TestResult): Completable {

        val history = prefs.historyResults
        val newHistory = history.plus(testResult)
        prefs.historyResults = newHistory

        val historyList = HashMap<String, Any>()
        historyList["history"] = newHistory

        return Completable.fromAction {
            db.collection(HISTORY_COLLECTION_NAME)
                .document(authInteractor.getProfile()?.email.orEmpty())
                .set(historyList)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

    fun getHistory(email: String): Completable =
        Completable.fromAction {
            db.collection(HISTORY_COLLECTION_NAME)
                .document(email)
                .get()
                .addOnSuccessListener { document ->
                    document?.let {
                        val serverHistory = it["history"] as java.util.ArrayList<TestResult>?
                        val historyDTO = Gson().toJson(serverHistory)
                        val history = Gson().fromJson(historyDTO, Array<TestResult>::class.java).toList()
                        if (!serverHistory.isNullOrEmpty()) {
                            prefs.historyResults = history
                        }
                    }
                }
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun getDoctorList(): Single<List<UserProfile>> =
        Single.create { emitter: SingleEmitter<List<UserProfile>> ->
            db.collection(USERS_COLLECTION_NAME)
                .whereEqualTo("role", "DOCTOR")
                .get()
                .addOnSuccessListener {
                    emitter.onSuccess(it.toObjects(UserProfile::class.java))
                }.addOnFailureListener(emitter::onError)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun getPatientsList(): Single<List<UserProfile>> =
        Single.create { emitter: SingleEmitter<List<UserProfile>> ->
            db.collection(USERS_COLLECTION_NAME)
                .whereEqualTo("currentDoctor", authInteractor.getProfile()?.email)
                .get()
                .addOnSuccessListener {
                    emitter.onSuccess(it.toObjects(UserProfile::class.java))
                }.addOnFailureListener(emitter::onError)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun getPatientsHistory(email: String): Single<List<TestResult>> =
        Single.create { emitter: SingleEmitter<List<TestResult>> ->
            db.collection(HISTORY_COLLECTION_NAME)
                .document(email)
                .get()
                .addOnSuccessListener {
                    val serverHistory = it["history"] as java.util.ArrayList<HashMap<String, String>>?
                    if (serverHistory.isNullOrEmpty()) {
                        emitter.onSuccess(emptyList())
                    } else {
                        val historyDTO = Gson().toJson(serverHistory)
                        emitter.onSuccess(Gson().fromJson(historyDTO, Array<TestResult>::class.java).toList())
                    }
                }.addOnFailureListener(emitter::onError)
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    companion object {
        private const val USERS_COLLECTION_NAME = "users"
        private const val HISTORY_COLLECTION_NAME = "history"
    }
}