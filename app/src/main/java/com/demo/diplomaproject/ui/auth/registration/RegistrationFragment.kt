package com.demo.diplomaproject.ui.auth.registration

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.extensions.gone
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.presentation.auth.registration.RegistrationPresenter
import com.demo.diplomaproject.presentation.auth.registration.RegistrationView
import kotlinx.android.synthetic.main.fragment_registration.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class RegistrationFragment : BaseFragment(), RegistrationView {

    override val layoutRes = R.layout.fragment_registration

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter =
        scope.getInstance(RegistrationPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }

    override fun showProgress(shouldShow: Boolean) {
        val a = 1
    }

    override fun showEmailErrorMessage(message: String) {
        emailLayout.error = message
        emailLayout.isErrorEnabled = true
    }

    override fun showPasswordErrorMessage(message: String) {
        passwordLayout.error = message
        passwordLayout.isErrorEnabled = true
    }

    override fun showConfirmPasswordErrorMessage(message: String) {
        confirmPasswordLayout.error = message
        confirmPasswordLayout.isErrorEnabled = true
    }

    override fun showNameEmptyErrorMessage(message: String) {
        nameLayout.error = message
        nameLayout.isErrorEnabled = true
    }

    override fun showSurnameEmptyErrorMessage(message: String) {
        surnameLayout.error = message
        surnameLayout.isErrorEnabled = true
    }

    override fun showRoleEmptyErrorMessage(message: String) {
        roleMenuLayout.error = message
        roleMenuLayout.isErrorEnabled = true
    }

    override fun showExperienceEmptyErrorMessage(message: String) {
        workExperienceLayout.error = message
        workExperienceLayout.isErrorEnabled = true
    }

    override fun showUniversityEmptyErrorMessage(message: String) {
        universityLayout.error = message
        universityLayout.isErrorEnabled = true
    }

    override fun showErrorDialog(message: String) {
        val a = message
    }

    private fun onRegisterClicked() {
        presenter.validateInputData(
            email = emailInput.text?.toString() ?: "",
            password = passwordInput.text?.toString() ?: "",
            confirmPassword = confirmPasswordInput.text?.toString() ?: "",
            name = nameInput.text?.toString() ?: "",
            surname = surnameInput.text?.toString() ?: "",
            role = roleMenu?.text?.toString() ?: "",
            experience = workExperienceInput?.text?.toString() ?: "",
            university = universityInput?.text?.toString() ?: "",
        )
    }

    private fun initViews() {
        val items = listOf("Пациент", "Доктор")
        val adapter = ArrayAdapter(requireContext(), R.layout.menu_item, items)
        roleMenu?.setAdapter(adapter)
        roleMenu?.addTextChangedListener {
            it?.let { role ->
                if (role.toString() == "Доктор") {
                    workExperienceLayout.visible()
                    universityLayout.visible()
                } else {
                    workExperienceLayout.gone()
                    universityLayout.gone()
                }
            }
        }

        emailInput.addTextChangedListener {
            emailLayout.isErrorEnabled = false
        }

        passwordInput.addTextChangedListener {
            passwordLayout.isErrorEnabled = false
        }

        confirmPasswordInput.addTextChangedListener {
            confirmPasswordLayout.isErrorEnabled = false
        }

        nameInput.addTextChangedListener {
            nameLayout.isErrorEnabled = false
        }

        surnameInput.addTextChangedListener {
            surnameLayout.isErrorEnabled = false
        }

        roleMenu.addTextChangedListener {
            roleMenuLayout.isErrorEnabled = false
        }

        workExperienceInput.addTextChangedListener {
            workExperienceLayout.isErrorEnabled = false
        }

        universityInput.addTextChangedListener {
            universityLayout.isErrorEnabled = false
        }

        registrationButton.setOnClickListener { onRegisterClicked() }
        registrationGoToLogin.setOnClickListener { presenter.goToLogin() }
    }
}