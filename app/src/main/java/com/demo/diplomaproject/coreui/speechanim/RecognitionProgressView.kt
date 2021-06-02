package com.demo.diplomaproject.coreui.speechanim

/**
 * Take from SpeechRecognitiwView library
 * https://github.com/zagum/SpeechRecognitionView
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class RecognitionProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint()
    private var animator: BarParamsAnimator? = null

    private var radius = 0
    private var spacing = 0
    private var rotationRadius = 0
    private var amplitude = 0
    private var density = 0f
    private var isInitialized = false

    private var animating = false

    private lateinit var barColors: IntArray
    private lateinit var barMaxHeights: IntArray

    init {
        paint = Paint()
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.color = Color.GRAY
        density = resources.displayMetrics.density
        radius = (CIRCLE_RADIUS_DP * density).toInt()
        spacing = (CIRCLE_SPACING_DP * density).toInt()
        rotationRadius = (ROTATION_RADIUS_DP * density).toInt()
        amplitude = (IDLE_FLOATING_AMPLITUDE_DP * density).toInt()
        if (density <= MDPI_DENSITY) {
            amplitude *= 2
        }
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        if (recognitionBars.isEmpty()) {
            initBars()
        } else if (changed) {
            recognitionBars.clear()
            initBars()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (recognitionBars.isEmpty()) {
            return
        }
        if (animating) {
            animator!!.animate()
        }
        for (i in recognitionBars.indices) {
            val bar = recognitionBars[i]
            paint.color = barColors[i]
            canvas.drawRoundRect(bar.rect, radius.toFloat(), radius.toFloat(), paint)
        }
        if (animating) {
            invalidate()
        }
    }

    /**
     * Starts animating view
     */
    fun play() {
        startIdleInterpolation()
        animating = true
    }

    /**
     * Stops animating view
     */
    fun stop() {
        if (animator != null) {
            animator!!.stop()
            animator = null
        }
        animating = false
        resetBars()
    }

    /**
     * Set different colors to bars in view
     *
     * @param colors - array with size = [.BARS_COUNT]
     */
    fun setColors(colors: IntArray?) {
        if (colors == null) return
        barColors = IntArray(BARS_COUNT)
        if (colors.size < BARS_COUNT) {
            System.arraycopy(colors, 0, barColors, 0, colors.size)
            for (i in colors.size until BARS_COUNT) {
                barColors[i] = colors[0]
            }
        } else {
            System.arraycopy(colors, 0, barColors, 0, BARS_COUNT)
        }
    }

    /**
     * Set sizes of bars in view
     *
     * @param heights - array with size = [.BARS_COUNT],
     * if not set uses default bars heights
     */
    fun setBarMaxHeightsInDp(heights: IntArray?) {
        if (heights == null) return
        barMaxHeights = IntArray(BARS_COUNT)
        if (heights.size < BARS_COUNT) {
            System.arraycopy(heights, 0, barMaxHeights, 0, heights.size)
            for (i in heights.size until BARS_COUNT) {
                barMaxHeights[i] = heights[0]
            }
        } else {
            System.arraycopy(heights, 0, barMaxHeights, 0, BARS_COUNT)
        }
    }

    /**
     * Set radius of circle
     *
     * @param radius - Default value = [.CIRCLE_RADIUS_DP]
     */
    fun setCircleRadiusInDp(radius: Int) {
        this.radius = (radius * density).toInt()
    }

    /**
     * Set spacing between circles
     *
     * @param spacing - Default value = [.CIRCLE_SPACING_DP]
     */
    fun setSpacingInDp(spacing: Int) {
        this.spacing = (spacing * density).toInt()
    }

    /**
     * Set idle animation amplitude
     *
     * @param amplitude - Default value = [.IDLE_FLOATING_AMPLITUDE_DP]
     */
    fun setIdleStateAmplitudeInDp(amplitude: Int) {
        this.amplitude = (amplitude * density).toInt()
    }

    /**
     * Set rotation animation radius
     *
     * @param radius - Default value = [.ROTATION_RADIUS_DP]
     */
    fun setRotationRadiusInDp(radius: Int) {
        this.rotationRadius = (radius * density).toInt()
    }

    fun onRmsChanged(rms: Float) {
        if (animator == null || rms < 1f) {
            return
        }
        if (animator !is RmsAnimator && !isInitialized) {
            startRmsInterpolation()
        }
        if (animator is RmsAnimator) {
            (animator as RmsAnimator).onRmsChanged(rms)
        }
    }

    private fun initBars() {
        val heights: List<Int> = initBarHeights()
        val firstCirclePosition: Int = measuredWidth / 4 - 2 * spacing - 4 * radius
        for (i in 0 until BARS_COUNT) {
            val x = firstCirclePosition + (2 * radius + spacing) * i
            val bar = RecognitionBar(
                x,
                measuredHeight / 2,
                2 * radius,
                heights[i],
                radius
            )
            recognitionBars.add(bar)
        }
    }

    private fun initBarHeights(): List<Int> {
        val barHeights: MutableList<Int> = ArrayList()
        for (i in 0 until BARS_COUNT) {
            barHeights.add((barMaxHeights[i] * density).toInt())
        }
        return barHeights
    }

    private fun resetBars() {
        for (bar in recognitionBars) {
            bar.y = measuredHeight / 2
            bar.height = radius * 2
            bar.update()
        }
    }

    private fun startIdleInterpolation() {
        animator = IdleAnimator(recognitionBars, amplitude)
        animator?.start()
    }

    private fun startRmsInterpolation() {
        resetBars()
        animator = RmsAnimator(recognitionBars)
        (animator as RmsAnimator).start()
        isInitialized = true
    }

    companion object {

        val BARS_COUNT = 10

        private val CIRCLE_RADIUS_DP = 5
        private val CIRCLE_SPACING_DP = 11
        private val ROTATION_RADIUS_DP = 25
        private val IDLE_FLOATING_AMPLITUDE_DP = 1

        private val MDPI_DENSITY = 1.5f

        private val recognitionBars: ArrayList<RecognitionBar> = ArrayList()
    }
}