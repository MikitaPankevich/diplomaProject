package com.demo.diplomaproject.presentation.auth.onboarding

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.presentation.auth.login.LoginView
import com.demo.diplomaproject.ui.auth.AuthScreens
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class OnboardingPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<OnboardingView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }

    fun onRegisterClicked() {
        flowRouter.navigateTo(AuthScreens.RegistrationScreen)
    }

    fun onLoginClicked() {
        flowRouter.navigateTo(AuthScreens.LoginScreen)
    }
}