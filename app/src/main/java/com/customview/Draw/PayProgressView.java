package com.customview.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2019-08-08 12:27
 * 修改备注：
 */
public class PayProgressView extends View {

    public PayProgressView(Context context) {
        super(context);
        intiPaint();
    }

    public PayProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiPaint();
    }

    private int donePaintRadius = 100;
    private int undonePaintRadius = 60;
    private int lineHeight = 200;


    private Paint donePaint;
    private Paint doneLinePaint;

    private Paint undonePaint;
    private Paint undoneLinePaint;

    private void intiPaint() {
        donePaint = new Paint();
        donePaint.setColor(Color.BLUE);
        donePaint.setAntiAlias(true);
        donePaint.setStyle(Paint.Style.FILL);
        donePaint.setStrokeCap(Paint.Cap.BUTT);

        doneLinePaint = new Paint();
        doneLinePaint.setColor(Color.BLUE);
        doneLinePaint.setAntiAlias(true);
        doneLinePaint.setStyle(Paint.Style.FILL);

        undoneLinePaint = new Paint();
        undoneLinePaint.setColor(Color.GRAY);
        undoneLinePaint.setAntiAlias(true);
        undoneLinePaint.setStyle(Paint.Style.FILL);
        undoneLinePaint.setStrokeCap(Paint.Cap.BUTT);

        undonePaint = new Paint();
        undonePaint.setColor(Color.GRAY);
        undonePaint.setAntiAlias(true);
        undonePaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = donePaintRadius * 2;
        int height = (donePaintRadius + lineHeight + undonePaintRadius) * 2;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(donePaintRadius, donePaintRadius, donePaintRadius, donePaint);

        int bottom = donePaintRadius * 2 + lineHeight;
        RectF doneLineReact = new RectF(donePaintRadius - 10, donePaintRadius * 2, donePaintRadius + 10, bottom);
        canvas.drawRect(doneLineReact, doneLinePaint);

        int top = bottom;

        bottom = top + lineHeight;

        RectF react = new RectF(donePaintRadius - 10, top, donePaintRadius + 10, bottom);
        canvas.drawRect(react, undoneLinePaint);

        canvas.drawCircle(donePaintRadius, undonePaintRadius + bottom, undonePaintRadius, undonePaint);

    }
}
