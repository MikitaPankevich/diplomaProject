package com.demo.diplomaproject.presentation.main.settings

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SettingsView : MvpView {

    fun showVersion(versionName: String)

    fun shouldShowPatientUI(shouldShow: Boolean)
}