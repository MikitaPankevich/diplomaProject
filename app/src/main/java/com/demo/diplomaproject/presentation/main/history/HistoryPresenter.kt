package com.demo.diplomaproject.presentation.main.history

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.model.data.storage.Prefs
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class HistoryPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val prefs: Prefs
) : BasePresenter<HistoryView>() {

    override fun attachView(view: HistoryView?) {
        super.attachView(view)

        val history = prefs.historyResults
        if (history.isNotEmpty()) {
            viewState.showHistory(history)
        }
    }

    fun onBackPressed() {
        flowRouter.exit()
    }
}