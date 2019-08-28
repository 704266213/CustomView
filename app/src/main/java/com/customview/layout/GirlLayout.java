package com.customview.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格布局
 *
 * @author xia
 */
public class GirlLayout extends ViewGroup {


    private int rowCount = 3;
    private int rowItemWidth;

    private int verticalLinesWidth = 0;
    private int horizontalLinesHeight = 0;

    private List<Integer> rowHeights = new ArrayList<>();
    private List<List<View>> allView = new ArrayList<>();

    public GirlLayout(Context context) {
        this(context, null);
    }

    public GirlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GirlLayout);
        rowCount = a.getInt(R.styleable.GirlLayout_rowCount, 3);
        verticalLinesWidth = a.getDimensionPixelSize(R.styleable.GirlLayout_verticalLinesWidth, 3);
        horizontalLinesHeight = a.getDimensionPixelSize(R.styleable.GirlLayout_horizontalLinesHeight, 3);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int parentHeight = 0;
        List<View> lineViews;
        if (widthMode == MeasureSpec.EXACTLY) {
            allView.clear();
            lineViews = new ArrayList<>();

            int lineHeight = 0;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                int childHeight = childView.getMeasuredHeight();

                if (i != 0 && i % rowCount == 0) {
                    rowHeights.add(lineHeight);
                    parentHeight += lineHeight;
                    lineHeight = 0;
                    allView.add(lineViews);
                    lineViews = new ArrayList<>();
                }

                lineHeight = Math.max(lineHeight, childHeight);
                lineViews.add(childView);
            }

            allView.add(lineViews);
            rowHeights.add(lineHeight);
            parentHeight += lineHeight;


            rowItemWidth = (width - (rowCount - 1) * verticalLinesWidth) / rowCount;

            int lineSize = allView.size();
            for (int i = 0; i < lineSize; i++) {
                List<View> rowViews = allView.get(i);
                int rowHeight = rowHeights.get(i);
                for (View childView : rowViews) {
                    LayoutParams layoutParams = childView.getLayoutParams();
                    layoutParams.width = rowItemWidth;
                    layoutParams.height = rowHeight;

                    int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, rowItemWidth);
                    int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, rowHeight);
                    childView.measure(childWidthSpec, childHeightSpec);
                }
            }

            parentHeight += (allView.size() + 1) * horizontalLinesHeight;


            setMeasuredDimension(width, parentHeight);
        } else {
            new Throwable("宽度必须为精确值");
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int rowTop = horizontalLinesHeight;
        int rowBottom;
        int lineSize = allView.size();

        for (int i = 0; i < lineSize; i++) {
            List<View> rowViews = allView.get(i);
            rowBottom = rowTop + rowHeights.get(i);

            int rowSize = rowViews.size();
            int left = 0;
            int right;
            for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
                View childView = rowViews.get(rowIndex);
                right = left + rowItemWidth;
                childView.layout(left, rowTop, right, rowBottom);
                left = right + horizontalLinesHeight;
            }
            rowTop = rowBottom + horizontalLinesHeight;
        }
    }


}
