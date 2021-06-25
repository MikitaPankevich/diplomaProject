package com.demo.diplomaproject.ui.main.settings.language

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.domain.interactor.Language
import com.demo.diplomaproject.extensions.invisible
import com.demo.diplomaproject.extensions.visible
import com.demo.diplomaproject.presentation.main.settings.language.LanguagePresenter
import com.demo.diplomaproject.presentation.main.settings.language.LanguageView
import kotlinx.android.synthetic.main.fragment_languages.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class LanguageFragment : BaseFragment(), LanguageView {

    override val layoutRes = R.layout.fragment_languages

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    @InjectPresenter
    lateinit var presenter: LanguagePresenter

    @ProvidePresenter
    fun providePresenter(): LanguagePresenter =
        scope.getInstance(LanguagePresenter::class.java)

    override fun installScopeModules(scope: Scope) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languageToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        englishLanguage.setOnClickListener { presenter.onLanguageClicked(Language.ENGLISH) }
        russianLanguage.setOnClickListener { presenter.onLanguageClicked(Language.RUSSIAN) }
    }

    override fun setSelected(language: Language) {
        englishChecked.invisible()
        russianChecked.invisible()
        when (language) {
            Language.ENGLISH -> {
                englishChecked.visible()
            }
            Language.RUSSIAN -> {
                russianChecked.visible()
            }
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun recreateActivity() {
        requireActivity().recreate()
        presenter.onBackPressed()
    }

    override fun showConfirmationDialog() {

        var alertDialog = AlertDialog.Builder(requireContext())
            .setPositiveButton(getString(R.string.ok)) { _: DialogInterface, _: Int ->
                presenter.onChangeLanguageConfirmed()
            }
            .setNegativeButton(getString(R.string.cancel)) { _: DialogInterface, _: Int -> }
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_confirmation_message))
            .create()

        alertDialog.show()
    }
}