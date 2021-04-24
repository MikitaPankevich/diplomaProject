package com.demo.diplomaproject.core.global.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulers : SchedulersProvider {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

    override fun trampoline() = Schedulers.trampoline()

    override fun newThread() = Schedulers.newThread()

    override fun io() = Schedulers.io()
}
