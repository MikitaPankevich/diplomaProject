package com.demo.diplomaproject.presentation.main.settings.pacients

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class PatientsPresenter @Inject constructor(
    private val router: Router,
    private val databaseInteractor: DatabaseInteractor,
    private val authInteractor: AuthInteractor
) : BasePresenter<PatientsView>() {

    fun onBackPressed() {
        router.exit()
    }

}