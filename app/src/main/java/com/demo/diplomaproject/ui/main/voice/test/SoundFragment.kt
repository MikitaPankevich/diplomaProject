package com.demo.diplomaproject.ui.main.voice.test

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.voice.test.sound.SoundPresenter
import com.demo.diplomaproject.presentation.main.voice.test.sound.SoundView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class SoundFragment : BaseFragment(), SoundView {

    override val layoutRes = R.layout.fragment_sound

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: SoundPresenter

    @ProvidePresenter
    fun providePresenter(): SoundPresenter =
        scope.getInstance(SoundPresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}