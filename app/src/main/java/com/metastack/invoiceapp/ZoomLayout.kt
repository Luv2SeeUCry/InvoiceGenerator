package com.metastack.invoiceapp

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
import kotlin.math.max
import kotlin.math.min

class ZoomLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var scaleFactor = 1.0f
    private val scaleDetector: ScaleGestureDetector

    init {
        scaleDetector = ScaleGestureDetector(context, ScaleListener())
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        canvas.scale(scaleFactor, scaleFactor)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = max(0.8f, min(scaleFactor, 2.5f)) // Limit zoom between 0.8x and 2.5x
            invalidate()
            requestLayout()
            return true
        }
    }
}
