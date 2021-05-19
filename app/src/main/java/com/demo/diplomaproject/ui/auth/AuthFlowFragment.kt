package com.demo.diplomaproject.ui.auth

import android.os.Bundle
import com.demo.diplomaproject.core.FlowFragment
import com.demo.diplomaproject.extensions.setLaunchScreen

class AuthFlowFragment : FlowFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigator.setLaunchScreen(AuthScreens.OnboardingScreen)
        }
    }
}
