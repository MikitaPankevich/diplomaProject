package com.demo.diplomaproject.presentation.main.settings.doctor

import com.demo.diplomaproject.domain.entity.UserProfile
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface DoctorView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDoctorList(doctors: List<UserProfile>)

    fun showNoDoctorView()

    fun showCurrentDoctor(doctor: UserProfile)
}