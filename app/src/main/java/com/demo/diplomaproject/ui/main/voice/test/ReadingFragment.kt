package com.demo.diplomaproject.ui.main.voice.test

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.voice.test.reading.ReadingPresenter
import com.demo.diplomaproject.presentation.main.voice.test.reading.ReadingView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class ReadingFragment : BaseFragment(), ReadingView {

    override val layoutRes = R.layout.fragment_reading

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: ReadingPresenter

    @ProvidePresenter
    fun providePresenter(): ReadingPresenter =
        scope.getInstance(ReadingPresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}