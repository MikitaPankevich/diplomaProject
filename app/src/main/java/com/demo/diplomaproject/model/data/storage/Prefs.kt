package com.demo.diplomaproject.model.data.storage

import android.content.Context
import com.demo.diplomaproject.domain.entity.UserProfile
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject

class Prefs @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {

    private val appPrefs by lazy { context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE) }

    var currentLocale: Locale?
        get() {
            val language = appPrefs.getString(CURRENT_LOCALE, null)
            return if (!language.isNullOrEmpty()) Locale(language) else null
        }
        set(value) {
            appPrefs.edit().putString(CURRENT_LOCALE, value?.language).apply()
        }

    var userProfile: UserProfile?
        get() {
            val userProfile = appPrefs.getString(USER_PROFILE, null)
            return if (!userProfile.isNullOrEmpty()) {
                gson.fromJson(userProfile, UserProfile::class.java)
            } else {
                null
            }
        }
        set(user) {
            appPrefs.edit().putString(USER_PROFILE, gson.toJson(user)).apply()
        }

    fun clearUserData() {
        userProfile = null
    }

    companion object {

        private const val APP_DATA = "APP_DATA"
        private const val CURRENT_LOCALE = "CURRENT_LOCALE"
        private const val USER_PROFILE = "USER_PROFILE"
    }
}
