package com.demo.diplomaproject.ui.main.voice.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.demo.diplomaproject.R
import com.demo.diplomaproject.core.BaseFragment
import com.demo.diplomaproject.di.DI
import com.demo.diplomaproject.model.data.service.SpeechService
import com.demo.diplomaproject.presentation.main.voice.test.sound.SoundPresenter
import com.demo.diplomaproject.presentation.main.voice.test.sound.SoundView
import kotlinx.android.synthetic.main.fragment_sound.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Scope

class SoundFragment : BaseFragment(), SoundView {

    override val layoutRes = R.layout.fragment_sound

    override val parentFragmentScopeName = DI.SERVER_SCOPE

    private var isServiceConnected = false

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceConnected = false
            presenter.onServiceDisconnected()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service != null) {
                isServiceConnected = true
                presenter.onServiceBind((service as SpeechService.SpeechServiceBinder).getService())
            }
        }
    }

    override fun installScopeModules(scope: Scope) {}

    @InjectPresenter
    lateinit var presenter: SoundPresenter

    @ProvidePresenter
    fun providePresenter(): SoundPresenter =
        scope.getInstance(SoundPresenter::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        soundPlay.setOnClickListener { presenter.onStartClicked() }
        val barsColor = requireContext().getColor(R.color.blue_nuke_end)
        val colors = intArrayOf(
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor,
            barsColor
        )
        soundSpeechView.setColors(colors)
        soundSpeechView.setBarMaxHeightsInDp(intArrayOf(55, 45, 60, 53, 42, 58, 49))
        soundSpeechView.play()
        soundToolbar.setNavigationOnClickListener { presenter.onBackPressed() }
    }

    override fun bindSpeechService() {
        val intent = Intent(context, SpeechService::class.java)
        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun unbindSpeechService() {
        if (isServiceConnected) {
            isServiceConnected = false
            requireContext().unbindService(serviceConnection)
        }
    }

    override fun playAnimation(rms: Float) {
        soundSpeechView.onRmsChanged(rms)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter.onBackPressed()
    }
}