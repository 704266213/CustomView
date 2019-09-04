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
 * 创建时间：2019-08-08 11:43
 * 修改备注：
 */
public class DotLineView extends View {

    public DotLineView(Context context) {
        super(context);

        intiPaint();
    }

    public DotLineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        intiPaint();
    }

    private int dotRadius = 10;
    private int dotInterval = 10;

    private Paint linePaint;
    private Paint dotPaint;

    private void intiPaint() {
        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeCap(Paint.Cap.BUTT);

        dotPaint = new Paint();
        dotPaint.setColor(Color.GRAY);
        dotPaint.setAntiAlias(true);
        dotPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, dotRadius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();

        int diameter = dotRadius * 2;
        int height = getHeight() > diameter ? getHeight() : diameter;


        int centerY = height / 2;
//        canvas.drawLine(0, centerY -1, width, centerY + 1, linePaint);

        //使用RectF构造
        RectF rect = new RectF(0f, centerY - 4, width, centerY + 4);
        canvas.drawRect(rect, linePaint);

        int count = width / (diameter + dotInterval) - 1;

        int start = (width- (diameter + dotInterval) * count) / 2;

        for (int i = start + dotRadius; i < width - start; i = i + diameter + dotInterval) {
            canvas.drawCircle(i, centerY, dotRadius, dotPaint);
        }

    }
}
