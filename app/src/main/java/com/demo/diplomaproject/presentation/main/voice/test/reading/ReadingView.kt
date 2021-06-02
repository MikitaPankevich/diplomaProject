package com.demo.diplomaproject.presentation.main.voice.test.reading

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReadingView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun bindSpeechService()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun unbindSpeechService()

    fun playAnimation(rms: Float)

    fun changeIcon(isStart: Boolean)
}