package com.demo.diplomaproject.ui.auth.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.extensions.onImeActionDone
import com.demo.diplomaproject.presentation.auth.login.LoginPresenter
import com.demo.diplomaproject.presentation.auth.login.LoginView
import kotlinx.android.synthetic.main.fragment_login.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class LoginFragment : BaseFragment(), LoginView {

    override val layoutRes = R.layout.fragment_login

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = scope.getInstance(LoginPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { onLoginClicked() }
        loginGoToRegistration.setOnClickListener { presenter.onRegisterClicked() }
        passwordInput.onImeActionDone { onLoginClicked() }

        emailInput.addTextChangedListener {
            emailLayout.isErrorEnabled = false
        }
        passwordInput.addTextChangedListener {
            passwordLayout.isErrorEnabled = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun showErrorDialog(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showEmailErrorMessage(message: String) {
        emailLayout.error = message
        emailLayout.isErrorEnabled = true
    }

    override fun showPasswordErrorMessage(message: String) {
        passwordLayout.error = message
        passwordLayout.isErrorEnabled = true
    }

    override fun showProgress(shouldShow: Boolean) {
        loginProgress.showProgress(shouldShow)
    }

    private fun onLoginClicked() {
        presenter.onLoginClicked(
            emailInput.text.toString().toLowerCase(),
            passwordInput.text.toString()
        )
    }
}