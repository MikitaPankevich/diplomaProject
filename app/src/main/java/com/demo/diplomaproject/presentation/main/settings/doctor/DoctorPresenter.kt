package com.demo.diplomaproject.presentation.main.settings.doctor

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class DoctorPresenter @Inject constructor(
    private val router: Router,
    private val databaseInteractor: DatabaseInteractor,
    private val authInteractor: AuthInteractor
) : BasePresenter<DoctorView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val currentDoctor = authInteractor.getProfile()?.currentDoctor

        if (!currentDoctor.isNullOrEmpty()) {
            databaseInteractor
                .getDoctorList()
                .subscribe(
                    { doctors ->
                        if (!doctors.isNullOrEmpty()) {
                            doctors
                                .find { it.email == currentDoctor }
                                ?.let { viewState.showCurrentDoctor(it) }
                        }
                    },
                    {}
                )
                .connect()
        } else {
            viewState.showNoDoctorView()
        }
    }


    fun onBackPressed() {
        router.exit()
    }

    fun onFindDoctorClicked() {
        databaseInteractor
            .getDoctorList()
            .subscribe(
                { if (!it.isNullOrEmpty()) viewState.showDoctorList(it) },
                {}
            )
            .connect()
    }

    fun onChooseDoctorClicked(doctorEmail: String) {
        val profile = authInteractor.getProfile()
        databaseInteractor
            .updateUserProfile(profile!!.copy(currentDoctor = doctorEmail))
            .subscribe(
                { router.exit() },
                {}
            )
            .connect()
    }

    fun onClearDoctorClicked() {
        val profile = authInteractor.getProfile()
        databaseInteractor
            .updateUserProfile(profile!!.copy(currentDoctor = ""))
            .subscribe(
                { viewState.showNoDoctorView() },
                {}
            )
            .connect()
    }
}