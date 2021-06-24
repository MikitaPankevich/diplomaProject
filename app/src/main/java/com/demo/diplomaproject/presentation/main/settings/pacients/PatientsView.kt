package com.demo.diplomaproject.presentation.main.settings.pacients

import com.demo.diplomaproject.domain.entity.UserProfile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PatientsView : MvpView {

    fun showPatients(patients: List<UserProfile>)

    fun showError()
}