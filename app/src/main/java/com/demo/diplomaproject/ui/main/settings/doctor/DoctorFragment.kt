package com.demo.diplomaproject.ui.main.settings.doctor

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.history.HistoryPresenter
import com.demo.diplomaproject.presentation.main.history.HistoryView
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class DoctorFragment : BaseFragment(), DoctorView {

    override val layoutRes = R.layout.fragment_about_doctor

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: DoctorPresenter

    @ProvidePresenter
    fun providePresenter(): DoctorPresenter =
        scope.getInstance(DoctorPresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}