package com.demo.diplomaproject.di.module

import com.demo.diplomaproject.core.ErrorHandler
import com.demo.diplomaproject.di.ServerPath
import com.demo.diplomaproject.di.provider.GsonProvider
import com.demo.diplomaproject.di.provider.OkHttpClientProvider
import com.demo.diplomaproject.di.provider.ServerApiProvider
import com.demo.diplomaproject.domain.interactor.AuthInteractor
import com.demo.diplomaproject.domain.interactor.DatabaseInteractor
import com.demo.diplomaproject.model.data.server.ServerApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import toothpick.config.Module

class ServerModule : Module() {

    init {
        // Network
        bind(OkHttpClient::class.java)
            .toProvider(OkHttpClientProvider::class.java)
            .providesSingletonInScope()
        bind(String::class.java)
            .withName(ServerPath::class.java)
            .toInstance("")
        bind(ServerApi::class.java)
            .toProvider(ServerApiProvider::class.java)
            .providesSingletonInScope()
        bind(ErrorHandler::class.java)
            .singletonInScope()
        bind(AuthInteractor::class.java)
            .singletonInScope()
        bind(DatabaseInteractor::class.java)
            .singletonInScope()
    }
}
