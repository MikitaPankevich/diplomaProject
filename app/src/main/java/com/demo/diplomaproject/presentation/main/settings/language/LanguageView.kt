package com.demo.diplomaproject.presentation.main.settings.language

import com.demo.diplomaproject.domain.interactor.Language
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LanguageView : MvpView {

    fun setSelected(language: Language)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun recreateActivity()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showConfirmationDialog()
}