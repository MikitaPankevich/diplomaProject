package com.demo.diplomaproject.presentation.main.settings

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.presentation.main.settings.profile.ProfileView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SettingsPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<SettingsView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}