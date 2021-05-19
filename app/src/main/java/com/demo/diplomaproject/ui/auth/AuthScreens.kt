package com.demo.diplomaproject.ui.auth

import com.demo.diplomaproject.ui.auth.login.LoginFragment
import com.demo.diplomaproject.ui.auth.onboarding.OnboardingFragment
import com.demo.diplomaproject.ui.auth.registration.RegistrationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen


object AuthScreens {

    object LoginScreen : SupportAppScreen() {

        override fun getFragment() = LoginFragment()
    }

    object RegistrationScreen : SupportAppScreen() {

        override fun getFragment() = RegistrationFragment()
    }

    object OnboardingScreen : SupportAppScreen() {

        override fun getFragment() = OnboardingFragment()
    }
}