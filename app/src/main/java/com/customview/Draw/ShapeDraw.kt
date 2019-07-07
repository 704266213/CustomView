package com.customview.Draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 *
 * 类描述：
 * 创建人：alan
 * 创建时间：2019-07-07 22:59
 * 修改备注：
 * @version
 *
 */
class ShapeDraw : View {

    constructor(context: Context) : super(context) {
        intiPaint()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiPaint()
    }


    private lateinit var circlePaint: Paint

    private lateinit var linePaint: Paint

    private lateinit var pointPaint: Paint

    private lateinit var rectPaint: Paint


    private fun intiPaint() {
        //设置画笔基本属性
        circlePaint = Paint()
        //抗锯齿功能
        circlePaint.isAntiAlias = true
        //设置画笔颜色
        circlePaint.color = Color.BLUE
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的
        circlePaint.style = Paint.Style.FILL_AND_STROKE
        //设置画笔宽度
        circlePaint.strokeWidth = 10.0f
        //设置阴影
        circlePaint.setShadowLayer(10.0f, 15.0f, 15.0f, Color.GREEN)


        //设置画笔基本属性
        linePaint = Paint()
        linePaint.color = Color.BLUE
        linePaint.isAntiAlias = true
        linePaint.strokeWidth = 10.0f
        linePaint.style = Paint.Style.STROKE


        //设置画笔基本属性
        pointPaint = Paint()
        pointPaint.color = Color.RED
        pointPaint.isAntiAlias = true
        pointPaint.strokeWidth = 20.0f
        pointPaint.style = Paint.Style.FILL


        //工具类RectF与Rect
        rectPaint = Paint()
        pointPaint.color = Color.RED
        pointPaint.isAntiAlias = true
        pointPaint.strokeWidth = 10.0f
        pointPaint.style = Paint.Style.STROKE


    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //设置画布背景颜色
        canvas!!.drawRGB(255, 255, 255)
        //画圆
        canvas.drawCircle(200.0f, 200.0f, 150.0f, circlePaint)


        //画直线
        //startX:开始点X坐标
        //startY:开始点Y坐标
        //stopX:结束点X坐标
        //stopY:结束点Y坐标
        canvas.drawLine(450.0f, 100.0f, 600.0f, 300.0f, linePaint)


        //float X：点的X坐标
        //float Y：点的Y坐标
        canvas.drawPoint(100.0f, 100.0f, pointPaint)

        //直接构造矩形
        canvas.drawRect(50f, 450f, 150f, 550f, pointPaint)


        //使用RectF构造
        val rect = RectF(120f, 10f, 210f, 100f)
        canvas.drawRect(rect, pointPaint)

        //使用Rect构造
        val rect2 = Rect(230, 10, 320, 100)
        canvas.drawRect(rect2, pointPaint)

        //圆角矩形
        //参数：
        //RectF rect:要画的矩形
        //float rx:生成圆角的椭圆的X轴半径
        //float ry:生成圆角的椭圆的Y轴半径

        val roundRect = RectF(200f, 450f, 300f, 550f)
        canvas.drawRoundRect(roundRect, 10f, 10f, pointPaint)


        //RectF oval:生成椭圆的矩形
        //float startAngle：弧开始的角度，以X轴正方向为0度
        //float sweepAngle：弧持续的角度
        //boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
        val rect1 = RectF(50f, 600f, 250f, 800f)
        canvas.drawArc(rect1, 0f, 90f, false, pointPaint)
    }


}