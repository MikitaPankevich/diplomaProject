package com.demo.diplomaproject.di.provider

import com.demo.diplomaproject.BuildConfig
import com.demo.diplomaproject.extensions.addLoggingInterceptor
import com.demo.diplomaproject.extensions.debug
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor() : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            debug { addLoggingInterceptor(HttpLoggingInterceptor.Level.BODY) }
            build()
        }
    }

    companion object {
        private const val TIMEOUT = 30L
    }
}
