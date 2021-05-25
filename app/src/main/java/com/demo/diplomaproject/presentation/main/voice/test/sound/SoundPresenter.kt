package com.demo.diplomaproject.presentation.main.voice.test.sound

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SoundPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<SoundView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}