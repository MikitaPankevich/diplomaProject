package com.demo.diplomaproject.ui.main.voice.test

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.voice.test.result.ResultPresenter
import com.demo.diplomaproject.presentation.main.voice.test.result.ResultView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class ResultFragment : BaseFragment(), ResultView {

    override val layoutRes = R.layout.fragment_result

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: ResultPresenter

    @ProvidePresenter
    fun providePresenter(): ResultPresenter =
        scope.getInstance(ResultPresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}