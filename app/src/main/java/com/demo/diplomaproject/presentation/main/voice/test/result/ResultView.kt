package com.demo.diplomaproject.presentation.main.voice.test.result

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ResultView : MvpView {


    fun showResult(qualityIndex: String, messageDescription: String, messageSuggestion: String)
}