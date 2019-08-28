package com.customview.Draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.customview.R

class PieChartView : View {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    private lateinit var paint: Paint
    private lateinit var outCircleSpacePain: Paint

    private lateinit var outCircleSpacePainSecond: Paint
    private lateinit var outCircleSpacePainThree: Paint
    private lateinit var outCircleSpacePainFour: Paint


    private lateinit var innerCirclePain: Paint

    private var outCircleWidth = 0
    private var outCircleSpace = 0f
    private var outCircleSpaceColor = 0
    private var startAngle = -90f
    private var innerCircleWidth = 0
    private var innerCircleWidthColor = 0

    private fun initView(attrs: AttributeSet) {
        initAttr(attrs)
        intiPaint()
    }

    private fun initAttr(attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PieChartView)
        outCircleWidth = attributes.getDimensionPixelSize(R.styleable.PieChartView_outCircleWidth, 10)
        outCircleSpace = attributes.getFloat(R.styleable.PieChartView_outCircleSpace, 10f)
        outCircleSpaceColor = attributes.getColor(
            R.styleable.PieChartView_outCircleSpaceColor,
            ContextCompat.getColor(context, R.color.color_FFFAFDFE)
        )
        startAngle = attributes.getFloat(R.styleable.PieChartView_startAngle, startAngle)

        innerCircleWidth = attributes.getDimensionPixelSize(R.styleable.PieChartView_innerCircleWidth, 10)
        innerCircleWidthColor = attributes.getColor(
            R.styleable.PieChartView_innerCircleWidthColor,
            ContextCompat.getColor(context, R.color.color_FFEFF5EE)
        )
        attributes.recycle()
    }

    private fun intiPaint() {
        //设置画笔基本属性
        paint = Paint()
        //抗锯齿功能
        paint.isAntiAlias = true
        //设置画笔颜色
        paint.color = ContextCompat.getColor(context, R.color.color_FF967FFD)
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的STROKE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = outCircleWidth.toFloat()


        outCircleSpacePain = Paint()
        //抗锯齿功能
        outCircleSpacePain.isAntiAlias = true
        //设置画笔颜色
        outCircleSpacePain.color = outCircleSpaceColor
        outCircleSpacePain.style = Paint.Style.STROKE
        outCircleSpacePain.strokeWidth = outCircleWidth.toFloat()

        innerCirclePain = Paint()
        //抗锯齿功能
        innerCirclePain.isAntiAlias = true
        //设置画笔颜色
        innerCirclePain.color = innerCircleWidthColor
        innerCirclePain.style = Paint.Style.STROKE
        innerCirclePain.strokeWidth = innerCircleWidth.toFloat()

        outCircleSpacePainSecond = Paint()
        //抗锯齿功能
        outCircleSpacePainSecond.isAntiAlias = true
        //设置画笔颜色
        outCircleSpacePainSecond.color = ContextCompat.getColor(context, R.color.color_FFFDCE3B)
        outCircleSpacePainSecond.style = Paint.Style.STROKE
        outCircleSpacePainSecond.strokeWidth = outCircleWidth.toFloat()

        outCircleSpacePainThree = Paint()
        //抗锯齿功能
        outCircleSpacePainThree.isAntiAlias = true
        //设置画笔颜色
        outCircleSpacePainThree.color = ContextCompat.getColor(context, R.color.color_FFDDE7FE)
        outCircleSpacePainThree.style = Paint.Style.STROKE
        outCircleSpacePainThree.strokeWidth = outCircleWidth.toFloat()

        outCircleSpacePainFour = Paint()
        //抗锯齿功能
        outCircleSpacePainFour.isAntiAlias = true
        //设置画笔颜色
        outCircleSpacePainFour.color = ContextCompat.getColor(context, R.color.color_FF3D79FE)
        outCircleSpacePainFour.style = Paint.Style.STROKE
        outCircleSpacePainFour.strokeWidth = outCircleWidth.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = if (measuredWidth > measuredHeight) measuredHeight else measuredWidth
        setMeasuredDimension(w, w)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val halfCircleWidth = outCircleWidth.toFloat() / 2
        val right = measuredWidth.toFloat() - halfCircleWidth
        val bottom = measuredHeight.toFloat() - halfCircleWidth

        val reactF = RectF(halfCircleWidth, halfCircleWidth, right, bottom)


        var startAngleTemp = startAngle + outCircleSpace

        canvas?.drawArc(reactF, startAngleTemp, outCircleSpace, false, outCircleSpacePain)
        startAngleTemp += outCircleSpace
        canvas?.drawArc(reactF, startAngleTemp, 100f, false, paint)

        startAngleTemp += 100
        canvas?.drawArc(reactF, startAngleTemp, outCircleSpace, false, outCircleSpacePain)
        startAngleTemp += outCircleSpace
        canvas?.drawArc(reactF, startAngleTemp, 70f, false, outCircleSpacePainSecond)

        startAngleTemp += 70f
        canvas?.drawArc(reactF, startAngleTemp, outCircleSpace, false, outCircleSpacePain)
        startAngleTemp += outCircleSpace
        canvas?.drawArc(reactF, startAngleTemp, 120f, false, outCircleSpacePainThree)

        startAngleTemp += 120f
        canvas?.drawArc(reactF, startAngleTemp, outCircleSpace, false, outCircleSpacePain)
        startAngleTemp += outCircleSpace
        canvas?.drawArc(reactF, startAngleTemp, 50f, false, outCircleSpacePainFour)


        var radius = measuredWidth.toFloat() / 2 - outCircleWidth.toFloat() - innerCircleWidth.toFloat() / 2
        canvas?.drawCircle(measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2, radius, innerCirclePain)
    }

}