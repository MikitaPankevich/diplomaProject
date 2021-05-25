package com.demo.diplomaproject.presentation.main.settings.theme

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ThemePresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<ThemeView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}