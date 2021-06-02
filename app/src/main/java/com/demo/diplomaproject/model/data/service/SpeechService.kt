package com.demo.diplomaproject.model.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.demo.diplomaproject.BuildConfig
import com.demo.diplomaproject.coreui.speechanim.RmsListener
import java.util.*

class SpeechService : Service() {

    lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechRecognizerIntent: Intent
    private var rmsListener: RmsListener? = null

    private val binder = SpeechServiceBinder()

    private val speechResultListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
        }

        override fun onBeginningOfSpeech() {
        }

        override fun onRmsChanged(rmsdB: Float) {
            rmsListener?.onRmsChanged(rmsdB)
        }

        override fun onBufferReceived(buffer: ByteArray?) {
        }

        override fun onEndOfSpeech() {
        }

        override fun onError(error: Int) {
        }

        override fun onResults(results: Bundle?) {
        }

        override fun onPartialResults(partialResults: Bundle?) {
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
        }
    }


    override fun onBind(intent: Intent?): IBinder? {

        return binder
    }

    override fun onCreate() {
        super.onCreate()

        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, BuildConfig.APPLICATION_ID)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(speechResultListener)
    }

    fun startListening() {
        speechRecognizer.startListening(speechRecognizerIntent)
    }


    fun stopListening() {
        speechRecognizer.stopListening()
        speechRecognizer.cancel()
    }

    fun setOnChangeRmsListener(listener: RmsListener) {
        rmsListener = listener
    }

    fun removeListeners() {
        rmsListener = null
    }

    override fun onDestroy() {
        super.onDestroy()

        speechRecognizer.stopListening()
        speechRecognizer.cancel()
        speechRecognizer.destroy()
    }

    inner class SpeechServiceBinder : Binder() {

        fun getService(): SpeechService = this@SpeechService
    }
}