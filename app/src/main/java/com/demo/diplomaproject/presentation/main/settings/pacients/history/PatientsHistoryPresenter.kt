package com.demo.diplomaproject.presentation.main.settings.pacients.history

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.domain.entity.UserProfile
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PatientsHistoryPresenter @Inject constructor(
    private val router: Router,
    private val databaseInteractor: DatabaseInteractor,
    private val patient: UserProfile
) : BasePresenter<PatientsHistoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        databaseInteractor
            .getPatientsHistory(patient.email)
            .subscribe(
                { if (it.isEmpty()) viewState.showError() else viewState.showHistory(it) },
                { viewState.showError() }
            )
            .connect()
    }

    fun onBackPressed() {
        router.exit()
    }
}