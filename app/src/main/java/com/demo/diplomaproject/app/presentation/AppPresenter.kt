package com.demo.diplomaproject.app.presentation

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.ErrorHandler
import com.demo.diplomaproject.core.Screens
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppPresenter @Inject constructor(
    private val router: Router,
    private val errorHandler: ErrorHandler,
    private val authInteractor: AuthInteractor
) : BasePresenter<AppView>() {

    override fun onDestroy() {
        super.onDestroy()

        errorHandler.onDestroy()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onAppStarted() {
        if (authInteractor.isLoggedIn()) {
            router.newRootScreen(Screens.MainFlow)
        } else {
            router.newRootScreen(Screens.AuthFlow)
        }
    }
}
