package com.demo.diplomaproject.presentation.main.voice

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.ui.main.voice.VoiceScreens
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class VoicePresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<VoiceView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }

    fun startTest() {
        flowRouter.startFlow(VoiceScreens.SoundScreen)
    }
}