package com.demo.diplomaproject.domain.interactor

import com.demo.diplomaproject.core.global.scheduler.SchedulersProvider
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.model.data.storage.Prefs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable
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

    fun updateUserProfile(email: String, userProfile: UserProfile): Completable =
        Completable
            .fromAction {
                db
                    .collection(USERS_COLLECTION_NAME)
                    .document(email)
                    .set(userProfile)
                    .addOnSuccessListener {
                        prefs.userProfile = userProfile
                    }
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    companion object {
        private const val USERS_COLLECTION_NAME = "users"
    }
}