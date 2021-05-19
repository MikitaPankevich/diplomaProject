package com.demo.diplomaproject.core

import com.demo.diplomaproject.ui.auth.AuthFlowFragment
import com.demo.diplomaproject.ui.main.MainFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainFlow : SupportAppScreen() {
        override fun getFragment() = MainFlowFragment()
    }

    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }
}
