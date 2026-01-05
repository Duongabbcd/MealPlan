package com.ezt.meal.ai.scan.screen.camera.customview

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.ezt.meal.ai.scan.R

val Int.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density

class SquareHoleOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }

    private val holeRect = RectF()
    private val cornerRadius = 12.dp

    // Expose hole rect
    fun getHoleRect(): RectF = holeRect

    init {
        setBackgroundColor(context.resources.getColor(R.color.black_0_5)) // semi-transparent black
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Example: hole is square, centered horizontally
        val horizontalMargin = 32.dp
        val topMargin = 110.dp

        val size = w - horizontalMargin * 2
        val left = horizontalMargin
        val top = topMargin

        holeRect.set(left, top, left + size, top + size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(holeRect, cornerRadius, cornerRadius, clearPaint)
    }
}
