package com.demo.diplomaproject.model.data.storage

import android.content.Context
import javax.inject.Inject

class Prefs @Inject constructor(
    private val context: Context
) {

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private val appPrefs by lazy { getSharedPreferences(APP_DATA) }

    companion object {

        private const val APP_DATA = "APP_DATA"
    }
}
