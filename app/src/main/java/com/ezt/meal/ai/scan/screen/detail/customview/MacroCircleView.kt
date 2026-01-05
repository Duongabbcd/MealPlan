package com.ezt.meal.ai.scan.screen.detail.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class MacroCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f // thickness of ring, adjust as needed
        strokeCap = Paint.Cap.BUTT
    }

    private val gapAngle = 3f // gap in degrees between segments

    var proteinPercent = 0f
    var carbPercent = 0f
    var fatPercent = 0f
    var totalPercent = 100f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) - paint.strokeWidth / 2

        val rect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        var startAngle = -90f

        fun drawSegment(percent: Float, color: Int) {
            if (percent <= 0f) return
            val sweepAngle = 360f * percent - gapAngle
            paint.color = color
            canvas.drawArc(rect, startAngle, sweepAngle, false, paint)
            startAngle += sweepAngle + gapAngle
        }

        // Order: protein (green), carb (blue), fat (orange)
        drawSegment(totalPercent, Color.parseColor("#FFFFFF"))
        drawSegment(proteinPercent, Color.parseColor("#90CD1F"))
        drawSegment(carbPercent, Color.parseColor("#F58638"))
        drawSegment(fatPercent, Color.parseColor("#4B93E5"))
    }
}

