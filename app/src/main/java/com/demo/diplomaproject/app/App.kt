package com.demo.diplomaproject.app

import android.app.Application
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.di.module.AppModule
import com.demo.diplomaproject.di.module.ServerModule
import com.demo.diplomaproject.extensions.debug
import com.google.firebase.FirebaseApp
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
        FirebaseApp.initializeApp(this)
    }

    private fun initDI() {
        Toothpick.setConfiguration(createToothPickConfiguration())

        Toothpick.openScope(DI.APP_SCOPE)
            .installModules(AppModule(this))

        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
            .installModules(ServerModule())
    }

    private fun createToothPickConfiguration(): Configuration {
        debug {
            return Configuration.forDevelopment().preventMultipleRootScopes()
        }
        return Configuration.forProduction()
    }
}
