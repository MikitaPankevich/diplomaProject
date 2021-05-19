package com.demo.diplomaproject.domain.interactor

import com.demo.diplomaproject.core.global.scheduler.SchedulersProvider
import com.demo.diplomaproject.model.data.storage.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.Completable
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val prefs: Prefs,
    private val schedulers: SchedulersProvider
) {

    private val auth: FirebaseAuth = Firebase.auth

    fun isLoggedIn() = auth.currentUser != null

    fun login(email: String, password: String): Completable =
        Completable
            .fromAction { auth.signInWithEmailAndPassword(email, password) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())


    fun registerUser(email: String, password: String): Completable =
        Completable
            .fromAction { auth.createUserWithEmailAndPassword(email, password) }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun logout(): Completable =
        Completable
            .fromAction {
                auth.signOut()
                prefs.clearUserData()
            }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())

    fun getUser(): FirebaseUser? = auth.currentUser
}