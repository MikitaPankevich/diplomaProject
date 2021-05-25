package com.demo.diplomaproject.ui.main.history

import android.os.Bundle
import android.view.View
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.auth.registration.RegistrationPresenter
import com.demo.diplomaproject.presentation.auth.registration.RegistrationView
import com.demo.diplomaproject.presentation.main.history.HistoryPresenter
import com.demo.diplomaproject.presentation.main.history.HistoryView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class HistoryFragment : BaseFragment(), HistoryView {

    override val layoutRes = R.layout.fragment_history

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter =
        scope.getInstance(HistoryPresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}