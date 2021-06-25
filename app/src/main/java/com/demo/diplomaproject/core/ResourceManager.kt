package com.demo.diplomaproject.core

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.demo.diplomaproject.model.data.storage.Prefs
import java.util.*
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context,
    private val prefs: Prefs
) {

    fun getString(id: Int) = getLocalizedResources().getString(id)

    fun getString(id: Int, vararg formatArgs: Any): String {
        return String.format(getLocalizedResources().getString(id, *formatArgs))
    }

    fun getStringArray(@ArrayRes id: Int): Array<String> =
        getLocalizedResources().getStringArray(id)

    private fun getLocalizedResources(): Resources {
        val res = context.resources
        val configuration = Configuration(res.configuration)
        prefs.currentLocale?.let {
            configuration.setLocale(Locale(it.language))
        }
        val localizedContext = context.createConfigurationContext(configuration)
        return localizedContext.resources
    }
}
