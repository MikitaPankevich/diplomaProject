package com.demo.diplomaproject.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutRes: Int

    protected open val parentFragmentScopeName by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: throw RuntimeException("${javaClass.simpleName} must have parent scope name!")
    }

    protected lateinit var scope: Scope

    private lateinit var fragmentScopeName: String

    private var isInstanceStateSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName =
            savedInstanceState?.getString(STATE_FRAGMENT_SCOPE_NAME) ?: simpleFragmentScopeName()
        val areScopeModulesInstalled = Toothpick.isScopeOpen(fragmentScopeName)
        scope = Toothpick.openScopes(parentFragmentScopeName, fragmentScopeName).apply {
            if (!areScopeModulesInstalled) {
                installScopeModules(this)
            }
        }
        Toothpick.inject(this, scope)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()

        isInstanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        isInstanceStateSaved = true
        outState.putString(STATE_FRAGMENT_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (shouldCloseScope()) {
            Toothpick.closeScope(scope.name)
        }
    }

    protected abstract fun installScopeModules(scope: Scope)

    open fun onBackPressed() {}

    private fun simpleFragmentScopeName() = "${javaClass.simpleName}_${hashCode()}"

    // Taken from Moxy MvpAppCompatFragment to close Toothpick scope.
    private fun shouldCloseScope(): Boolean {
        return when {
            activity?.isFinishing == true -> true
            isInstanceStateSaved -> false
            else -> isRemoving || isParentRemoving()
        }
    }

    private fun isParentRemoving(): Boolean {
        var anyParentIsRemoving = false
        var parent = parentFragment

        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        return anyParentIsRemoving
    }

    companion object {
        private const val STATE_FRAGMENT_SCOPE_NAME = "state_scope_name"
    }
}
