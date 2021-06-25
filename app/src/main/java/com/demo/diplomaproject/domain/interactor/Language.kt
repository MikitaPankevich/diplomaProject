package com.demo.diplomaproject.domain.interactor

enum class Language {
    ENGLISH,
    RUSSIAN;

    fun toLocale() = when (this) {
        ENGLISH -> "en"
        RUSSIAN -> "ru"
    }
}