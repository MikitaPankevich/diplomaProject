package com.demo.diplomaproject.presentation.main.settings.language

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.core.Screens
import com.demo.diplomaproject.domain.interactor.Language
import com.demo.diplomaproject.domain.interactor.LanguageInteractor
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LanguagePresenter @Inject constructor(
    private val languageInteractor: LanguageInteractor,
    private val router: Router
) : BasePresenter<LanguageView>() {

    private var currentLanguage = Language.ENGLISH

    private lateinit var lastSelectedLanguage: Language

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        currentLanguage = languageInteractor.currentLanguage
        viewState.setSelected(currentLanguage)
    }

    fun onLanguageClicked(language: Language) {
        if (language == currentLanguage) {
            return
        }
        lastSelectedLanguage = language
        viewState.showConfirmationDialog()
    }

    fun onBackPressed() {
        router.exit()
    }

    fun onChangeLanguageConfirmed() {
        languageInteractor.saveNewLanguage(lastSelectedLanguage)
        viewState.recreateActivity()
        router.newRootScreen(Screens.MainFlow)
    }
}