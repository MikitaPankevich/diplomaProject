package com.demo.diplomaproject.presentation.main.settings

import com.demo.diplomaproject.BuildConfig
import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.core.Screens
import com.demo.diplomaproject.domain.entity.ParticipateType
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.presentation.main.settings.profile.ProfileView
import com.demo.diplomaproject.ui.main.settings.SettingsScreens
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SettingsPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val authInteractor: AuthInteractor
) : BasePresenter<SettingsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.showVersion(BuildConfig.VERSION_NAME)
    }

    override fun attachView(view: SettingsView?) {
        super.attachView(view)

        viewState.shouldShowPatientUI(authInteractor.getProfile()?.role == ParticipateType.PATIENT)
    }


    fun onBackPressed() {
        flowRouter.exit()
    }

    fun onAboutDoctorClicked() {
        flowRouter.startFlow(SettingsScreens.DoctorScreen)
    }

    fun onLogoutClicked() {
        authInteractor
            .logout()
            .subscribe(
                {flowRouter.newRootFlow(Screens.AuthFlow)},
                {}
            )
            .connect()
    }

    fun onPatientsListClicked() {
        flowRouter.startFlow(SettingsScreens.PatientsScreen)
    }
}