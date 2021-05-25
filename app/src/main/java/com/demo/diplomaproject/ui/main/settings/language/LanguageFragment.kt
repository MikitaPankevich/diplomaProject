package com.demo.diplomaproject.ui.main.settings.language

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.settings.language.LanguagePresenter
import com.demo.diplomaproject.presentation.main.settings.language.LanguageView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class LanguageFragment : BaseFragment(), LanguageView {

    override val layoutRes = R.layout.fragment_languages

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: LanguagePresenter

    @ProvidePresenter
    fun providePresenter(): LanguagePresenter =
        scope.getInstance(LanguagePresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}