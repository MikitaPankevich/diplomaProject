package com.demo.diplomaproject.presentation.main.history

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class HistoryPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<HistoryView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}