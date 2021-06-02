package com.demo.diplomaproject.coreui.speechanim

/**
 * Take from SpeechRecognitiwView library
 * https://github.com/zagum/SpeechRecognitionView
 */
class RmsAnimator(
    recognitionBars: List<RecognitionBar>
) : BarParamsAnimator {

    private val barAnimators: ArrayList<BarRmsAnimator> = ArrayList()

    init {
        for (bar in recognitionBars) {
            barAnimators.add(BarRmsAnimator(bar))
        }
    }

    override fun start() {
        for (barAnimator in barAnimators) {
            barAnimator.start()
        }
    }

    override fun stop() {
        for (barAnimator in barAnimators) {
            barAnimator.stop()
        }
    }

    override fun animate() {
        for (barAnimator in barAnimators) {
            barAnimator.animate()
        }
    }

    fun onRmsChanged(rmsDB: Float) {
        for (barAnimator in barAnimators) {
            barAnimator.onRmsChanged(rmsDB)
        }
    }
}