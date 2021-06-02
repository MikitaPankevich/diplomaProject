package com.demo.diplomaproject.presentation.main.voice.test.sound

import android.os.CountDownTimer
import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.coreui.speechanim.RmsListener
import com.demo.diplomaproject.model.data.service.SpeechService
import com.demo.diplomaproject.ui.main.voice.VoiceScreens
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SoundPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<SoundView>() {

    private var speechService: SpeechService? = null
    private val rmsResult = arrayListOf<Float>()

    private val soundTimer = object : CountDownTimer(SOUND_TIMEOUT, SOUND_TIMEOUT) {

        override fun onFinish() {
            viewState.unbindSpeechService()
            router.navigateTo(VoiceScreens.ReadingScreen(rmsResult.average().toLong()))
        }

        override fun onTick(millisUntilFinished: Long) {}
    }

    override fun onDestroy() {
        super.onDestroy()

        soundTimer.cancel()
        viewState.unbindSpeechService()
    }

    fun onBackPressed() {
        soundTimer.cancel()
        viewState.unbindSpeechService()
        router.exit()
    }

    fun onServiceBind(service: SpeechService) {
        service.apply {
            speechService = this
            setOnChangeRmsListener(object : RmsListener {
                override fun onRmsChanged(rms: Float) {
                    rmsResult.add(rms)
                    viewState.playAnimation(rms)
                }
            })
            startListening()
        }
        soundTimer.start()
    }

    fun onServiceDisconnected() {
        speechService?.removeListeners()
        speechService?.stopListening()
        speechService = null
    }

    fun onStartClicked() {
        viewState.bindSpeechService()
    }

    companion object {
        private const val SOUND_TIMEOUT = 6000L
    }
}