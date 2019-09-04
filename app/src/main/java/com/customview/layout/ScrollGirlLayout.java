package com.customview.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.Scroller;

import com.customview.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollGirlLayout extends ViewGroup {


    private int rowCount = 3;
    private int rowItemWidth;

    private int touchSlop;
//    private Scroller scroller;

    private OverScroller overScroller;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private VelocityTracker mVelocityTracker;

    private int measureHeight;
    private int viewHeight;

    private int verticalLinesWidth = 0;
    private int horizontalLinesHeight = 0;

    private List<Integer> rowHeights = new ArrayList<>();
    private List<List<View>> allView = new ArrayList<>();

    public ScrollGirlLayout(Context context) {
        this(context, null);
    }

    public ScrollGirlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollGirlLayout);
        rowCount = a.getInt(R.styleable.ScrollGirlLayout_rowCount, 3);
        verticalLinesWidth = a.getDimensionPixelSize(R.styleable.ScrollGirlLayout_verticalLinesWidth, 3);
        horizontalLinesHeight = a.getDimensionPixelSize(R.styleable.ScrollGirlLayout_horizontalLinesHeight, 3);
        a.recycle();

//        scroller = new Scroller(getContext());
        overScroller = new OverScroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        touchSlop = viewConfiguration.getScaledPagingTouchSlop();
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量的可见View的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 测量的可见View的高度
        measureHeight = heightSize;
        Log.e("XLog", "====================== measureHeight :" + measureHeight);
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

            viewHeight = parentHeight;

            //测量出的实际的View的高度
            Log.e("XLog", "====================== viewHeight : " + viewHeight);

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


    private boolean intercepted;

    private int lastX;
    private int lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                lastX = currentX;
                lastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = currentX - lastX;
                int offsetY = currentY - lastY;
                //Math.abs(offsetY) >= touchSlop &&
                if (Math.abs(offsetY) > Math.abs(offsetX)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        lastX = currentX;
        lastY = currentY;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);

        int currentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                lastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:

                //getScrollY() 是界面的偏移量，
                Log.e("XLog", "========================= ScrollY:" + getScrollY());

                //viewHeight - measureHeight 表示可滚动的最大值及最大偏移量
                //第一步 finalY 滚动后的位置的坐标（即滚动后当前手指的坐标）滑动完成后的坐标，垂直方向，
                if (getScrollY() > 0 && getScrollY() < (viewHeight - measureHeight)) {
                    int offsetY = lastY - currentY;
                    overScroller.startScroll(0, overScroller.getFinalY(), 0, offsetY);
                }

                //注意，一定要进行invalidate刷新界面，触发computeScroll()方法，
                // 因为单纯的startScroll()是属于Scroller的，只是一个辅助类，并不会触发界面的绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) velocityTracker.getYVelocity();
                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
                    fling(-initialVelocity);
                } else if (overScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0,
                        (viewHeight - measureHeight))) {
                    postInvalidateOnAnimation();
                }
                break;
        }
        lastY = currentY;
        return true;
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void fling(int velocityY) {
        int height = measureHeight;
        int bottom = viewHeight;
        overScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
                Math.max(0, bottom - height), 0, 0);

        postInvalidateOnAnimation();
    }

    @Override
    public void computeScroll() {
        //第二步 判断滚动偏移是否完成
        if (overScroller.computeScrollOffset()) {
            //第三步 更新滚动偏移， 滑动过程中，根据消耗的时间计算出的当前的滑动偏移距离，垂直方向
            scrollTo(0, overScroller.getCurrY());
            invalidate();
        }
    }

}
