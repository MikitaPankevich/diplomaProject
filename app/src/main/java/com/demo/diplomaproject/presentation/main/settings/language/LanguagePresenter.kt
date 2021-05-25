package com.demo.diplomaproject.presentation.main.settings.language

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class LanguagePresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<LanguageView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}