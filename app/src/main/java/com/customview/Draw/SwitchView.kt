package com.customview.Draw

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 *
 * 类描述：
 * 创建人：alan
 * 创建时间：2019-07-07 22:11
 * 修改备注：
 * @version
 *
 */
class SwitchView : View, View.OnClickListener {


    constructor(context: Context) : super(context) {
        intiPaint()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiPaint()
    }


    private lateinit var paintSwitchOpenBg: Paint
    private lateinit var paintSwitchCloseBg: Paint


    private lateinit var paintCircle: Paint


    private fun intiPaint() {
        //设置画笔基本属性
        paintSwitchOpenBg = Paint()
        //抗锯齿功能
        paintSwitchOpenBg.isAntiAlias = true
        //设置画笔颜色
        paintSwitchOpenBg.color = Color.GREEN
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的
        paintSwitchOpenBg.style = Paint.Style.FILL
        paintSwitchOpenBg.strokeCap = Paint.Cap.ROUND


        //设置画笔基本属性
        paintSwitchCloseBg = Paint()
        //抗锯齿功能
        paintSwitchCloseBg.isAntiAlias = true
        //设置画笔颜色
        paintSwitchCloseBg.color = Color.GRAY
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的
        paintSwitchCloseBg.style = Paint.Style.FILL
        paintSwitchCloseBg.strokeCap = Paint.Cap.ROUND


        //设置画笔基本属性
        paintCircle = Paint()
        //抗锯齿功能
        paintCircle.isAntiAlias = true
        //设置画笔颜色
        paintCircle.color = Color.WHITE
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的
        paintCircle.style = Paint.Style.FILL

        setOnClickListener(this)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(widthMeasureSpec, 400)
    }

    private var currentValue = 1f
    private var isOpen = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectF = RectF(100f, 100f, 500f, 300f)

        if (isOpen) {
            canvas?.drawRoundRect(rectF, 100f, 100f, paintSwitchOpenBg)

            val cx = 200f * currentValue + 200f
            canvas?.drawCircle(cx, 200f, 90f, paintCircle)
        } else {
            canvas?.drawRoundRect(rectF, 100f, 100f, paintSwitchCloseBg)

            val cx = 400f - 200f * currentValue
            canvas?.drawCircle(cx, 200f, 90f, paintCircle)
        }

//        canvas?.drawCircle(400f, 200f, 90f, paintCircle)


    }


    override fun onClick(p0: View?) {
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 200
        valueAnimator.addUpdateListener {
            currentValue = it.animatedValue.toString().toFloat()
            invalidate()
            Log.d("XLog", "current value is $currentValue")
        }
        valueAnimator.start()

        isOpen = !isOpen
    }


}