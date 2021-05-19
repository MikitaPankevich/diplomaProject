package com.demo.diplomaproject.ui.main

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.FlowFragment
import com.demo.diplomaproject.presentation.main.MainFlowPresenter
import com.demo.diplomaproject.presentation.main.MainFlowView
import kotlinx.android.synthetic.main.fragment_main_flow.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainFlowFragment : FlowFragment(), MainFlowView {

    override val layoutRes = R.layout.fragment_main_flow

    @InjectPresenter
    lateinit var presenter: MainFlowPresenter

    @ProvidePresenter
    fun providePresenter(): MainFlowPresenter =
        scope.getInstance(MainFlowPresenter::class.java)

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}