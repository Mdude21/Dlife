package com.example.developerslife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : View(context, attrs, defaultAttrs) {

    private val painter = Paint().apply{
        color = Color.DKGRAY
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            val centerX = (width / 2).toFloat()
            val centerY = (width / 2).toFloat()
            drawColor(Color.WHITE)
            drawCircle(centerX, centerY, SMILE_RADIUS, painter)
            painter.color = Color.WHITE
            drawCircle(centerX, centerY, EYE_RADIUS, painter)
            painter.color = Color.DKGRAY
            drawLine(centerX - 83, centerY, centerX + 80, centerY, painter)
            drawLine(centerX, centerY - 80, centerX - 80, centerY, painter)
            drawLine(centerX, centerY + 80, centerX - 80, centerY, painter)
        }
    }

    private companion object{
        private const val SMILE_RADIUS = 100f
        private const val EYE_RADIUS = 90f
    }
}