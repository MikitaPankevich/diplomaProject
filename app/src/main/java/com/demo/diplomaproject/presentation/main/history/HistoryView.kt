package com.demo.diplomaproject.presentation.main.history

import com.demo.diplomaproject.domain.entity.TestResult
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HistoryView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showHistory(history: MutableList<TestResult>)
}