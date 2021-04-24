package com.demo.diplomaproject.di.module

import android.content.Context
import com.demo.diplomaproject.app.App
import com.demo.diplomaproject.core.ErrorHandler
import com.demo.diplomaproject.core.ResourceManager
import com.demo.diplomaproject.core.global.scheduler.AppSchedulers
import com.demo.diplomaproject.core.global.scheduler.SchedulersProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(
    app: App
) : Module() {

    init {
        bind(Context::class.java)
            .toInstance(app)
        bind(SchedulersProvider::class.java)
            .toInstance(AppSchedulers())
        bind(ResourceManager::class.java)
            .singletonInScope()
        bind(ErrorHandler::class.java)
            .singletonInScope()

        // Navigation
        val cicerone = Cicerone.create()
        bind(Router::class.java)
            .toInstance(cicerone.router)
        bind(NavigatorHolder::class.java)
            .toInstance(cicerone.navigatorHolder)
    }
}
