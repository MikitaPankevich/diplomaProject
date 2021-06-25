package com.demo.diplomaproject.presentation.main.settings.profile

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<ProfileView>() {

    fun onBackPressed() {
        flowRouter.exit()
    }
}