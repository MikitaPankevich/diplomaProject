package com.demo.diplomaproject.ui.main.settings.theme

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.settings.theme.ThemePresenter
import com.demo.diplomaproject.presentation.main.settings.theme.ThemeView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class ThemeFragment : BaseFragment(), ThemeView {

    override val layoutRes = R.layout.fragment_theme

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: ThemePresenter

    @ProvidePresenter
    fun providePresenter(): ThemePresenter =
        scope.getInstance(ThemePresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}