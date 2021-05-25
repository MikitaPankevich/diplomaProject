package com.demo.diplomaproject.ui.main.voice

import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorPresenter
import com.demo.diplomaproject.presentation.main.settings.doctor.DoctorView
import com.demo.diplomaproject.presentation.main.voice.VoicePresenter
import com.demo.diplomaproject.presentation.main.voice.VoiceView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class VoiceFragment : BaseFragment(), VoiceView {

    override val layoutRes = R.layout.fragment_voice

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: VoicePresenter

    @ProvidePresenter
    fun providePresenter(): VoicePresenter =
        scope.getInstance(VoicePresenter::class.java)

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}