package com.demo.diplomaproject.presentation.main.voice.test.result

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.FlowRouter
import com.demo.diplomaproject.core.ResourceManager
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
    private val databaseInteractor: DatabaseInteractor,
    private val resourceManager: ResourceManager,
) : BasePresenter<ResultView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val messageDescription = if (testResult.qualityIndex.toDouble() < 4.0) {
            resourceManager.getString(R.string.test_result_bad)
        } else {
            resourceManager.getString(R.string.test_result_good)
        }
        val messageSuggestion = if (testResult.qualityIndex.toDouble() < 4.0) {
            resourceManager.getString(R.string.test_suggestion_bad)
        } else {
            resourceManager.getString(R.string.test_suggestion_good)
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