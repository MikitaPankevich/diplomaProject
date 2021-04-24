package com.demo.diplomaproject.app.presentation

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.ErrorHandler
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppPresenter @Inject constructor(
    private val router: Router,
    private val errorHandler: ErrorHandler
) : BasePresenter<AppView>() {

    override fun onDestroy() {
        super.onDestroy()

        errorHandler.onDestroy()
    }

    fun onBackPressed() {
        router.exit()
    }
}
