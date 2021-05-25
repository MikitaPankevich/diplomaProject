package com.demo.diplomaproject.presentation.main.voice.test.reading

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.presentation.main.history.HistoryView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ReadingPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<ReadingView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}