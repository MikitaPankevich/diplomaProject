package com.demo.diplomaproject.presentation.main.settings.pacients.history

import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.domain.entity.UserProfile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PatientsHistoryView : MvpView {

    fun showHistory(history: List<TestResult>)

    fun showError()
}