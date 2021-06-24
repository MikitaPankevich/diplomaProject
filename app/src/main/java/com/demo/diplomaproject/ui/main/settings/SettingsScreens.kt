package com.demo.diplomaproject.ui.main.settings

import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.ui.main.settings.doctor.DoctorFragment
import com.demo.diplomaproject.ui.main.settings.language.LanguageFragment
import com.demo.diplomaproject.ui.main.settings.pacients.PatientsFragment
import com.demo.diplomaproject.ui.main.settings.pacients.history.PatientsHistoryFragment
import com.demo.diplomaproject.ui.main.settings.profile.ProfileFragment
import com.demo.diplomaproject.ui.main.settings.theme.ThemeFragment
import com.demo.diplomaproject.ui.main.voice.test.ReadingFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object SettingsScreens {

    object ThemeScreen : SupportAppScreen() {

        override fun getFragment() = ThemeFragment()
    }

    object ProfileScreen : SupportAppScreen() {

        override fun getFragment() = ProfileFragment()
    }

    object LanguageScreen : SupportAppScreen() {

        override fun getFragment() = LanguageFragment()
    }

    object DoctorScreen : SupportAppScreen() {

        override fun getFragment() = DoctorFragment()
    }

    object PatientsScreen : SupportAppScreen() {

        override fun getFragment() = PatientsFragment()
    }

    data class PatientHistoryScreen(
        private val patient: UserProfile
    ) : SupportAppScreen() {

        override fun getFragment() = PatientsHistoryFragment.newInstance(patient)
    }
}