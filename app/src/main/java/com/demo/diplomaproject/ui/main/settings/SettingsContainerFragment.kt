package com.demo.diplomaproject.ui.main.settings

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.SettingsPresenter
import com.demo.diplomaproject.presentation.main.settings.SettingsView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class SettingsContainerFragment : BaseFragment(), SettingsView {

    override val layoutRes = R.layout.fragment_settings

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.flowContainer_fl) as? BaseFragment

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter = scope.getInstance(SettingsPresenter::class.java)

    override fun installScopeModules(scope: Scope) {}
}
