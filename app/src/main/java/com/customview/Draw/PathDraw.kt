package com.customview.Draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2019-07-07 23:52
 * 修改备注：
 */
class PathDraw : View {

    constructor(context: Context) : super(context) {
        intiPaint()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiPaint()
    }

    private lateinit var pathPaint: Paint
    private lateinit var path: Path

    private lateinit var bitmap: Bitmap


    private fun intiPaint() {
        //设置画笔基本属性
        pathPaint = Paint()
        //抗锯齿功能
        pathPaint.isAntiAlias = true
        //设置画笔颜色
        pathPaint.color = Color.BLUE
        //设置填充样式
        // Style.FILL 全部填充即沾满这个区域
        // Style.FILL_AND_STROKE
        // Style.STROKE 边框即中间为空的
        pathPaint.style = Paint.Style.FILL

        path = Path()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设定起始点
        path.moveTo(10f, 10f)
        //第一条直线的终点，也是第二条直线的起点
        path.lineTo(10f, 100f)
        //画第二条直线
        path.lineTo(100f, 100f)
        //第三条直线
        path.lineTo(500f, 100f)
        //闭环
        path.close()
        canvas!!.drawPath(path, pathPaint)
    }
}
