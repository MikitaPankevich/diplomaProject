package com.demo.diplomaproject.presentation.main.settings.pacients

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.ui.main.settings.SettingsScreens
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PatientsPresenter @Inject constructor(
    private val router: Router,
    private val databaseInteractor: DatabaseInteractor
) : BasePresenter<PatientsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        databaseInteractor
            .getPatientsList()
            .subscribe(
                { if (it.isEmpty()) viewState.showError() else viewState.showPatients(it) },
                { viewState.showError() }
            )
            .connect()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onPatientChosen(patient: UserProfile) {
        router.navigateTo(SettingsScreens.PatientHistoryScreen(patient))
    }
}