package com.demo.diplomaproject.presentation.main

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.presentation.main.BottomNavigationTabs.VOICE_TAB_TAG
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainFlowPresenter @Inject constructor(
    private val router: Router,
) : BasePresenter<MainFlowView>() {

    private val tabClickQueue = linkedSetOf<String>()

    fun onCreate(isFirstTime: Boolean) {
        if (isFirstTime) {
            viewState.initBottomTabFragments()
        }
    }

    fun onBottomMenuItemSelected(tag: String) {
        if (tabClickQueue.isNotEmpty() && tabClickQueue.last() == tag) return

        tabClickQueue.remove(tag)
        tabClickQueue.add(tag)
        viewState.replaceFragment(tag)
    }

    fun onBackPressed() {
        when {
            tabClickQueue.isEmpty() ||
                    (tabClickQueue.size == 1 && tabClickQueue.last() == VOICE_TAB_TAG) -> {
                router.exit()
            }
            tabClickQueue.size == 1 && tabClickQueue.last() != VOICE_TAB_TAG -> {
                tabClickQueue.remove(tabClickQueue.last())
                viewState.changeBottomTab(VOICE_TAB_TAG)
            }
            else -> {
                tabClickQueue.remove(tabClickQueue.last())
                val lastFragment = tabClickQueue.last()
                viewState.changeBottomTab(lastFragment)
                viewState.replaceFragment(lastFragment)
            }
        }
    }

    fun onNavigateToFragmentByTagFromChild(tag: String) {
        viewState.changeBottomTab(tag)
    }
}