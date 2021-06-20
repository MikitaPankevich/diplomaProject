package com.demo.diplomaproject.presentation.main.voice.test.reading

import com.demo.diplomaproject.core.BasePresenter
import com.demo.diplomaproject.coreui.speechanim.RmsListener
import com.demo.diplomaproject.di.PrimitiveWrapper
import com.demo.diplomaproject.domain.entity.TestResult
import com.demo.diplomaproject.model.data.service.SpeechService
import com.demo.diplomaproject.ui.main.voice.VoiceScreens
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class ReadingPresenter @Inject constructor(
    private val router: Router,
    private val averageRms: PrimitiveWrapper<Long>
) : BasePresenter<ReadingView>() {

    private var speechService: SpeechService? = null
    private val rmsResult = arrayListOf<Float>()

    override fun onDestroy() {
        super.onDestroy()

        viewState.unbindSpeechService()
    }

    fun onBackPressed() {
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
            viewState.changeIcon(false)
            startListening()
        }
    }

    fun onServiceDisconnected() {
        speechService?.removeListeners()
        speechService?.stopListening()
        speechService = null
    }

    fun onStartClicked() {
        viewState.bindSpeechService()
    }

    fun onStopClicked() {
        viewState.unbindSpeechService()
        viewState.changeIcon(true)
        val minRms = (rmsResult.minOrNull() ?: 0.0F)
        val maxRms = (rmsResult.maxOrNull() ?: 0.0F)
        val result = TestResult(
            averageRms.value.toString(),
            minRms.toString(),
            maxRms.toString(),
            ((maxRms - minRms) / averageRms.value).toString(),
            System.currentTimeMillis()
        )

        router.navigateTo(VoiceScreens.ResultScreen(result))
    }
}