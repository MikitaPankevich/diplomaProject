package com.demo.diplomaproject.domain.interactor

import com.demo.diplomaproject.core.global.scheduler.SchedulersProvider
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.model.data.storage.Prefs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        Completable
            .fromAction {
                db
                    .collection(USERS_COLLECTION_NAME)
                    .document(email)
                    .get()
                    .addOnSuccessListener { document ->
                        document?.let {
                            prefs.userProfile = it.toObject(UserProfile::class.java)
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
        history.add(testResult)
        prefs.historyResults = history

        val historyList = HashMap<String, Any>()
        historyList["history"] = history

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
                        val serverHistory = it.toObject(Array<TestResult>::class.java)
                        serverHistory?.let { history ->
                            prefs.historyResults = history.toMutableList()
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

    companion object {
        private const val USERS_COLLECTION_NAME = "users"
        private const val HISTORY_COLLECTION_NAME = "history"
    }
}