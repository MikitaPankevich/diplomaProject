package com.demo.diplomaproject.ui.auth.onboarding

import android.os.Bundle
import android.view.View
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.auth.onboarding.OnboardingPresenter
import com.demo.diplomaproject.presentation.auth.onboarding.OnboardingView
import kotlinx.android.synthetic.main.fragment_onboarding.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class OnboardingFragment : BaseFragment(), OnboardingView {

    override val layoutRes = R.layout.fragment_onboarding

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: OnboardingPresenter

    @ProvidePresenter
    fun providePresenter(): OnboardingPresenter = scope.getInstance(OnboardingPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onboardingLoginButton.setOnClickListener { presenter.onLoginClicked() }
        onboardingRegistrationButton.setOnClickListener { presenter.onRegisterClicked() }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}