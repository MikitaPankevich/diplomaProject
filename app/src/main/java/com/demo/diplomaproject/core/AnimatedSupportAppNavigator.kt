package com.demo.diplomaproject.core

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.demo.diplomaproject.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class AnimatedSupportAppNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    containerId: Int,
    @AnimatorRes @AnimRes private val enter: Int = R.animator.slide_left_enter,
    @AnimatorRes @AnimRes private val exit: Int = R.animator.fade_out_exit,
    @AnimatorRes @AnimRes private val popEnter: Int = R.animator.fade_in_enter,
    @AnimatorRes @AnimRes private val popExit: Int = 0
) : SupportAppNavigator(activity, fragmentManager, containerId) {
    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    }
}