package com.demo.diplomaproject.coreui.speechanim

import android.graphics.RectF

/**
 * Take from SpeechRecognitiwView library
 * https://github.com/zagum/SpeechRecognitionView
 */
class RecognitionBar(
    var x: Int,
    var y: Int,
    var height: Int,
    val maxHeight: Int,
    val radius: Int
) {

    val rect: RectF = RectF(
        (x - radius).toFloat(),
        (y - height / 2).toFloat(),
        (x + radius).toFloat(),
        (y + height / 2).toFloat()
    )

    fun update() {
        rect[x - radius.toFloat(), y - height / 2.toFloat(), x + radius.toFloat()] =
            y + height / 2.toFloat()
    }
}