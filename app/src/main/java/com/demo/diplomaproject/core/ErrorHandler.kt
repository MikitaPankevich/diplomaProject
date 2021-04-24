package com.demo.diplomaproject.core

import com.demo.diplomaproject.R
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun handleError(exception: Throwable, messageListener: (String) -> Unit = {}) {
        when (exception) {
            is TimeoutException -> {
                messageListener(resourceManager.getString(R.string.timeout_error))
            }
            is UnknownHostException -> {
                messageListener(resourceManager.getString(R.string.network_error))
            }
            else -> {
                messageListener(resourceManager.getString(R.string.unknown_error))
            }
        }
    }

    fun onDestroy() {
    }
}
