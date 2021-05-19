package com.demo.diplomaproject.coreui.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.demo.diplomaproject.R
import com.demo.diplomaproject.extensions.gone
import com.demo.diplomaproject.extensions.visible

class WidgetAppProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_widget_app_progress, this)

        alpha = PROGRESS_BACKGROUND_ALPHA
        setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray))
        isClickable = true
        isFocusableInTouchMode = true
        isFocusable = true
    }

    fun showProgress(show: Boolean) {
        if (show) {
            this.visible()
        } else {
            this.gone()
        }
    }

    companion object {
        private const val PROGRESS_BACKGROUND_ALPHA = 0.8f
    }
}