package com.customview.Draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TouchDrawView : View {


    constructor(context: Context) : super(context) {
        intiPaint()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiPaint()
    }

    private lateinit var paint: Paint
    private lateinit var path: Path

    private fun intiPaint() {
        paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        path = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path, paint)
    }

    private var preX = 0f
    private var preY = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val pointX = event?.x ?: 0f
        val pointY = event?.y ?: 0f
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(pointX, pointY)
                preX = pointX
                preY = pointY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
//                path.lineTo(pointX, pointY)
                val endX = (preX + pointX) / 2
                val endY = (preY + pointY) / 2

                path.quadTo(preX, preY, endX, endY)

                preX = pointX
                preY = pointY
                postInvalidate()
            }
            MotionEvent.ACTION_UP -> {

            }
        }

        return super.onTouchEvent(event)
    }

    public fun reset() {
        path.reset()
        postInvalidate()
    }
}