package com.demo.diplomaproject.domain.interactor

import android.content.Context
import android.content.res.Configuration
import com.demo.diplomaproject.model.data.storage.Prefs
import java.util.*
import javax.inject.Inject

class LanguageInteractor @Inject constructor(
    private val prefs: Prefs
) {

    var currentLanguage = setupCurrentLanguage()
        private set

    fun saveNewLanguage(language: Language) {
        prefs.currentLocale = resolveLocale(language)
        currentLanguage = language
    }

    fun configureContextBasedOnLanguage(context: Context): Context {
        return prefs.currentLocale?.let {
            val newConfig = Configuration(context.resources.configuration)
            Locale.setDefault(it)
            newConfig.setLocale(it)
            context.createConfigurationContext(newConfig)
        } ?: context
    }

    private fun resolveLocale(language: Language) = when (language) {
        Language.RUSSIAN -> LOCALE_RUSSIAN
        Language.ENGLISH -> Locale.ENGLISH
    }

    private fun setupCurrentLanguage(): Language {
        var locale = prefs.currentLocale
        if (locale == null) {
            locale = setupCurrentLocale()
        }

        return when (locale) {
            LOCALE_RUSSIAN -> Language.RUSSIAN
            else -> Language.ENGLISH
        }
    }

    private fun setupCurrentLocale(): Locale {
        val locale = when (Locale.getDefault().language) {
            LOCALE_RUSSIAN.language -> LOCALE_RUSSIAN
            else -> Locale.ENGLISH
        }
        prefs.currentLocale = locale
        return locale
    }

    companion object {
        private val LOCALE_RUSSIAN = Locale("ru")
    }
}