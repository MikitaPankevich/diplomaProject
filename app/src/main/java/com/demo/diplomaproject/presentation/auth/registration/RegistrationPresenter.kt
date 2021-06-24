package com.demo.diplomaproject.presentation.auth.registration

import android.util.Patterns
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.*
import com.demo.diplomaproject.domain.entity.ParticipateType
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.presentation.auth.AuthProperties.MAX_PASSWORD_LENGTH
import com.demo.diplomaproject.presentation.auth.AuthProperties.MIN_PASSWORD_LENGTH
import com.demo.diplomaproject.ui.auth.AuthScreens
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RegistrationPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val errorHandler: ErrorHandler,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor,
    private val databaseInteractor: DatabaseInteractor
) : BasePresenter<RegistrationView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }

    fun goToLogin() {
        flowRouter.navigateTo(AuthScreens.LoginScreen)
    }

    fun validateInputData(
        email: String,
        password: String,
        confirmPassword: String,
        name: String,
        surname: String,
        role: String,
        experience: String,
        university: String
    ) {
        val isEmailValid =
            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        val isPasswordsMatch = if (password != confirmPassword) {
            viewState.showConfirmPasswordErrorMessage(
                resourceManager.getString(R.string.not_the_same_password_error)
            )
            false
        } else true

        val isNameFilled = if (name.isEmpty()) {
            viewState.showNameEmptyErrorMessage("Field should be filled")
            false
        } else true

        val isSurnameFilled = if (surname.isEmpty()) {
            viewState.showSurnameEmptyErrorMessage("Field should be filled")
            false
        } else true

        val userRole = when (role) {
            "Doctor" -> ParticipateType.DOCTOR
            "Patient" -> ParticipateType.PATIENT
            else -> ParticipateType.CLEAR
        }
        val isRoleFilled = if (userRole != ParticipateType.CLEAR) {
            true
        } else {
            viewState.showRoleEmptyErrorMessage("Field should be filled")
            false
        }

        val isExperienceFilled = when (userRole) {
            ParticipateType.PATIENT -> true
            ParticipateType.DOCTOR -> {
                if (experience.isNotEmpty()) {
                    true
                } else {
                    viewState.showExperienceEmptyErrorMessage("Field should be filled")
                    false
                }
            }
            ParticipateType.CLEAR -> false
        }

        val isUniversityFilled = when (userRole) {
            ParticipateType.PATIENT -> true
            ParticipateType.DOCTOR -> {
                if (university.isNotEmpty()) {
                    true
                } else {
                    viewState.showUniversityEmptyErrorMessage("Field should be filled")
                    false
                }
            }
            ParticipateType.CLEAR -> false
        }

        if (isEmailValid && isExperienceFilled && isNameFilled && isPasswordsMatch
            && isRoleFilled && isUniversityFilled && isPasswordValid && isSurnameFilled
        ) {
            authInteractor
                .registerUser(email, password)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe(
                    {
                        databaseInteractor.updateUserProfile(
                            UserProfile(
                                email,
                                name,
                                surname,
                                userRole,
                                experience,
                                university
                            )
                        )
                            .subscribe(
                                { flowRouter.newRootScreen(Screens.MainFlow) },
                                { ex -> errorHandler.handleError(ex) { viewState.showErrorMessage(it) } }
                            )
                    },
                    { ex -> errorHandler.handleError(ex) { viewState.showErrorMessage(it) } }
                )
                .connect()
        }
    }
}