package com.demo.diplomaproject.presentation.auth.login

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorDialog(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmailErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPasswordErrorMessage(message: String)

    fun showProgress(shouldShow: Boolean)
}