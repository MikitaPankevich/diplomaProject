package com.demo.diplomaproject.ui.main.voice

import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.ui.main.voice.test.ReadingFragment
import com.demo.diplomaproject.ui.main.voice.test.ResultFragment
import com.demo.diplomaproject.ui.main.voice.test.SoundFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object VoiceScreens {

    object SoundScreen : SupportAppScreen() {

        override fun getFragment() = SoundFragment()
    }

    data class ReadingScreen(
        private val averageRms: Long
    ) : SupportAppScreen() {

        override fun getFragment() = ReadingFragment.newInstance(averageRms)
    }

    data class ResultScreen(
        private val testResult: TestResult
    ) : SupportAppScreen() {

        override fun getFragment() = ResultFragment.newInstance(testResult)
    }
}