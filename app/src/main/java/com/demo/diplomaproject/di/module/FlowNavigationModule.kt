package com.demo.diplomaproject.di.module

import com.demo.diplomaproject.core.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class FlowNavigationModule(
    globalRouter: Router
) : Module() {

    init {
        val cicerone = Cicerone.create(FlowRouter(globalRouter))
        bind(FlowRouter::class.java)
            .toInstance(cicerone.router)
        bind(NavigatorHolder::class.java)
            .toInstance(cicerone.navigatorHolder)
    }
}
