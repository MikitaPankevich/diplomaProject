package com.demo.diplomaproject.presentation.main.voice.test.result

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<ResultView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}