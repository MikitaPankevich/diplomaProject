package com.demo.diplomaproject.presentation.auth.login

import android.util.Patterns
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.*
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.presentation.auth.AuthProperties.MAX_PASSWORD_LENGTH
import com.demo.diplomaproject.presentation.auth.AuthProperties.MIN_PASSWORD_LENGTH
import com.demo.diplomaproject.ui.auth.AuthScreens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val errorHandler: ErrorHandler,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor,
    private val databaseInteractor: DatabaseInteractor
) : BasePresenter<LoginView>() {

    fun onLoginClicked(email: String, password: String) {
        val isEmailValid = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            viewState.showEmailErrorMessage(
                resourceManager.getString(R.string.incorrect_email_error)
            )
            false
        } else true

        val isPasswordValid = if (
            password.length < MIN_PASSWORD_LENGTH ||
            password.length > MAX_PASSWORD_LENGTH
        ) {
            viewState.showPasswordErrorMessage(
                resourceManager.getString(R.string.short_password_error)
            )
            false
        } else true

        if (isEmailValid && isPasswordValid) {
            authInteractor
                .login(email, password)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe(
                    {
                        databaseInteractor
                            .getProfile(email)
                            .subscribe(
                                {
                                    flowRouter.newRootScreen(Screens.MainFlow)},
                                { errorHandler.handleError(it) }
                            )
                        databaseInteractor.getHistory(email).subscribe({},{})
                    },
                    { errorHandler.handleError(it) }
                )
                .connect()
        }
    }

    fun onBackPressed() {
        flowRouter.exit()
    }

    fun onRegisterClicked() {
        flowRouter.navigateTo(AuthScreens.RegistrationScreen)
    }
}