package com.demo.diplomaproject.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainFlowView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun changeBottomTab(fragment: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun replaceFragment(tag: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun initBottomTabFragments()
}