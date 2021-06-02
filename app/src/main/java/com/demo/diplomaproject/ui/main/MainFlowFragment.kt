package com.demo.diplomaproject.ui.main

import android.os.Bundle
import android.view.View
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.core.FlowFragment
import com.demo.diplomaproject.presentation.main.BottomNavigationTabs.CURRENT_TAB_TAG
import com.demo.diplomaproject.presentation.main.BottomNavigationTabs.HISTORY_TAB_TAG
import com.demo.diplomaproject.presentation.main.BottomNavigationTabs.SETTINGS_TAB_TAG
import com.demo.diplomaproject.presentation.main.BottomNavigationTabs.VOICE_TAB_TAG
import com.demo.diplomaproject.presentation.main.MainFlowNavigationListener
import com.demo.diplomaproject.presentation.main.MainFlowPresenter
import com.demo.diplomaproject.presentation.main.MainFlowView
import com.demo.diplomaproject.ui.main.history.HistoryFragment
import com.demo.diplomaproject.ui.main.settings.SettingsContainerFragment
import com.demo.diplomaproject.ui.main.voice.VoiceFragment
import kotlinx.android.synthetic.main.fragment_main_flow.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainFlowFragment : FlowFragment(), MainFlowView, MainFlowNavigationListener {

    override val layoutRes = R.layout.fragment_main_flow

    @InjectPresenter
    lateinit var presenter: MainFlowPresenter

    private var currentTabTag = VOICE_TAB_TAG

    private var isFirstTime = true

    @ProvidePresenter
    fun providePresenter(): MainFlowPresenter =
        scope.getInstance(MainFlowPresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTabTag = savedInstanceState?.getString(CURRENT_TAB_TAG) ?: VOICE_TAB_TAG

        presenter.onCreate(isFirstTime)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainFlowBottomNavigation.setOnNavigationItemSelectedListener {
            val tag = when (it.itemId) {
                R.id.item_voice -> VOICE_TAB_TAG
                R.id.item_history -> HISTORY_TAB_TAG
                R.id.item_settings -> SETTINGS_TAB_TAG
                else -> throw UnsupportedOperationException("ItemId = ${it.itemId} not found!")
            }
            presenter.onBottomMenuItemSelected(tag)
            true
        }
    }

    override fun onBackPressed() {
        val fragment = childFragmentManager.findFragmentByTag(currentTabTag) as BaseFragment?
        fragment?.onBackPressed() ?: presenter.onBackPressed()
    }

    override fun initBottomTabFragments() {
        // No need  to recreate fragments when app died because its saved by the system
        if (childFragmentManager.fragments.isEmpty()) {
            isFirstTime = false
            val voiceFragment = VoiceFragment()
            val historyFragment = HistoryFragment()
            val settingsFragment = SettingsContainerFragment()

            childFragmentManager.beginTransaction()
                .add(R.id.flowContainer_fl, voiceFragment, VOICE_TAB_TAG)
                .add(R.id.flowContainer_fl, historyFragment, HISTORY_TAB_TAG)
                .add(R.id.flowContainer_fl, settingsFragment, SETTINGS_TAB_TAG)
                .hide(settingsFragment)
                .hide(historyFragment)
                .hide(voiceFragment)
                .commitNow()
        }
        navigateByTag(currentTabTag)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(CURRENT_TAB_TAG, currentTabTag)
        outState.putBoolean(IS_FIRST_TIME, isFirstTime)
    }

    override fun onBackPressedFromChild() {
        presenter.onBackPressed()
    }

    override fun navigateByTag(tag: String) {
        presenter.onNavigateToFragmentByTagFromChild(tag)
    }

    override fun changeBottomTab(fragment: String) {
        mainFlowBottomNavigation.selectedItemId = when (tag) {
            VOICE_TAB_TAG -> R.id.item_voice
            HISTORY_TAB_TAG -> R.id.item_history
            SETTINGS_TAB_TAG -> R.id.item_settings
            else -> R.id.item_voice
        }
    }

    override fun replaceFragment(tag: String) {
        childFragmentManager.apply {
            findFragmentByTag(currentTabTag)?.let { currentFragment ->
                findFragmentByTag(tag)?.let { selectedFragment ->
                    beginTransaction()
                        .setCustomAnimations(
                            R.animator.slide_left_enter,
                            R.animator.fade_out_exit
                        )
                        .hide(currentFragment)
                        .show(selectedFragment)
                        .commit()
                    currentTabTag = tag
                }
            }
        }
    }

    companion object {
        private const val IS_FIRST_TIME = "IS_FIRST_TIME"
    }
}