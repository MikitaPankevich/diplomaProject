package com.demo.diplomaproject.ui.main.voice.test

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.extensions.tryToGetParcelable
import com.demo.diplomaproject.presentation.main.voice.test.result.ResultPresenter
import com.demo.diplomaproject.presentation.main.voice.test.result.ResultView
import kotlinx.android.synthetic.main.fragment_result.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope
import toothpick.config.Module

class ResultFragment : BaseFragment(), ResultView {

    override val layoutRes = R.layout.fragment_result

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    override fun installScopeModules(scope: Scope) {
        val result = tryToGetParcelable<TestResult>(KEY_RESULT)
        scope.installModules(object : Module() {
            init {
                bind(TestResult::class.java).toInstance(result)
            }
        })
    }

    @InjectPresenter
    lateinit var presenter: ResultPresenter

    @ProvidePresenter
    fun providePresenter(): ResultPresenter =
        scope.getInstance(ResultPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        resultGotButton.setOnClickListener { presenter.onGotItClicked() }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun showResult(
        qualityIndex: String,
        messageDescription: String,
        messageSuggestion: String
    ) {
        resultText.text = qualityIndex
        resultDescription.text = messageDescription
        resultSuggestion.text = messageSuggestion
    }

    companion object {
        private const val KEY_RESULT = "KEY_RESULT"

        fun newInstance(testResult: TestResult) =
            ResultFragment().apply {
                arguments = bundleOf(
                    KEY_RESULT to testResult
                )
            }
    }
}