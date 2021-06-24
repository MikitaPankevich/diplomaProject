package com.demo.diplomaproject.presentation.auth.registration

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegistrationView : MvpView {

    fun showProgress(shouldShow: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showEmailErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showPasswordErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showConfirmPasswordErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showNameEmptyErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSurnameEmptyErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRoleEmptyErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showExperienceEmptyErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showUniversityEmptyErrorMessage(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorMessage(message: String)
}