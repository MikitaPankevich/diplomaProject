package com.demo.diplomaproject.coreui.speechanim

import kotlin.math.sin

/**
 * Take from SpeechRecognitiwView library
 * https://github.com/zagum/SpeechRecognitionView
 */
class IdleAnimator(
    private var bars: List<RecognitionBar>,
    private var floatingAmplitude: Int = 0
) : BarParamsAnimator {

    private var startTimestamp: Long = 0
    private var isPlaying = false

    override fun start() {
        isPlaying = true
        startTimestamp = System.currentTimeMillis()
    }

    override fun stop() {
        isPlaying = false
    }

    override fun animate() {
        if (isPlaying) {
            update(bars)
        }
    }

    private fun update(bars: List<RecognitionBar>) {
        val currTimestamp = System.currentTimeMillis()
        if (currTimestamp - startTimestamp > IDLE_DURATION) {
            startTimestamp += IDLE_DURATION
        }
        val delta = currTimestamp - startTimestamp
        for ((i, bar) in bars.withIndex()) {
            updateCirclePosition(bar, delta, i)
        }
    }

    private fun updateCirclePosition(
        bar: RecognitionBar,
        delta: Long,
        num: Int
    ) {
        val angle = delta.toFloat() / IDLE_DURATION * 360f + 120f * num
        val y = (sin(Math.toRadians(angle.toDouble())) * floatingAmplitude).toInt() + bar.y
        bar.y = y
        bar.update()
    }

    companion object {
        private val IDLE_DURATION: Long = 1500
    }
}