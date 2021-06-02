package com.demo.diplomaproject.presentation.main

interface MainFlowNavigationListener {

    fun navigateByTag(tag: String)

    fun onBackPressedFromChild()
}