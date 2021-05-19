package com.demo.diplomaproject.presentation.main

import com.demo.diplomaproject.core.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainFlowPresenter @Inject constructor(
    private val router: Router,
) : BasePresenter<MainFlowView>() {


    fun onBackPressed() {
        router.exit()
    }
}