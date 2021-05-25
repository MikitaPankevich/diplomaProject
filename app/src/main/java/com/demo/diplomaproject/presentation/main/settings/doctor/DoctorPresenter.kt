package com.demo.diplomaproject.presentation.main.settings.doctor

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class DoctorPresenter @Inject constructor(
    private val flowRouter: FlowRouter
) : BasePresenter<DoctorView>() {


    fun onBackPressed() {
        flowRouter.exit()
    }
}