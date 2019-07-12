package com.customview.Draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint.ANTI_ALIAS_FLAG


class XfermodeDraw : View {

    constructor(context: Context) : super(context) {
        intiPaint()
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        intiPaint()
    }

    private val widthDraw = 400
    private val heightDraw = 400
    private lateinit var dstBmp: Bitmap
    private lateinit var srcBmp: Bitmap
    private lateinit var mPaint: Paint

    private fun intiPaint() {
        dstBmp = makeDst(widthDraw, heightDraw)
        srcBmp = makeSrc(widthDraw, heightDraw)
        mPaint = Paint()
    }

    private fun makeDst(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val p = Paint(ANTI_ALIAS_FLAG)
        p.color = -0x33bc
        canvas.drawOval(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        return bitmap
    }

    private fun makeSrc(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val p = Paint(ANTI_ALIAS_FLAG)
        p.color = -0x995501
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), p)
        return bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //新建图层
        val layerID = canvas!!.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), mPaint)

        canvas.drawBitmap(dstBmp, 0f, 0f, mPaint)
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(srcBmp, widthDraw / 2f, widthDraw / 2f, mPaint)
        mPaint.xfermode = null
        //还原图层
        canvas.restoreToCount(layerID)
    }


}