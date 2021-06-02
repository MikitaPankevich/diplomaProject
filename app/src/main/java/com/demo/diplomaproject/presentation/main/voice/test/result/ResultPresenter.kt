package com.demo.diplomaproject.presentation.main.voice.test.result

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.core.Screens
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.ui.main.voice.VoiceScreens
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor(
    private val router: Router,
    private val testResult: TestResult,
    private val databaseInteractor: DatabaseInteractor
) : BasePresenter<ResultView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val messageDescription = if (testResult.qualityIndex.toDouble() < 3.5) {
            "The test found significant changes in your voice that can be caused by a serious condition in the larynx."
        } else {
            "The test did not reveal any abnormalities"
        }
        val messageSuggestion = if (testResult.qualityIndex.toDouble() < 3.5) {
            "You need to visit an otolaryngologist"
        } else {
            "Everything is fine"
        }
        viewState.showResult(testResult.qualityIndex, messageDescription, messageSuggestion)
        databaseInteractor.updateHistory(testResult).subscribe({},{}).connect()
    }


    fun onBackPressed() {
        router.exit()
    }

    fun onGotItClicked() {
        router.newRootScreen(Screens.MainFlow)
    }
}