package com.mydemo.FlowViewActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HaoPz on 2017/11/29.
 * 流布局：多用于 sku 或者 搜索历史
 */

public class AutoLineView extends ViewGroup {

    public AutoLineView(Context context) {
        this(context, null);
    }

    public AutoLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("*******", "super 构造");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // ViewGroup 需要测量 子View
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        // 测量 布局
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(widthMeasureSpec, heightMeasureSpec));
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("*******", "执行 onLayout 方法");
        layoutView(); // 测量 View
    }

    private void layoutView() {
        int paddingLeft = getPaddingLeft();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        // 拿到宽度和高度
        int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        // 设置一个可用的宽度,高度
        int avaialbeWidth = viewWidth;
        int avaialbeHeight = 0;

        // 可用宽度 ：已经使用的宽度
        int currentWidth = 0;
        int currentHeight = 0;

        int childCount = getChildCount();
        // 遍历所有的孩子,拿到每一个孩子的宽度和高度
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt == null) {
                continue;
            }
            if (childAt.getVisibility() == View.GONE) { // 已经隐藏,不考虑
                continue;
            }
            int childAtMeasuredWidth = childAt.getMeasuredWidth();
            int childAtMeasuredHeight = childAt.getMeasuredHeight();

            int childPaddingLeft = childAt.getPaddingLeft();
            int childAtPaddingTop = childAt.getPaddingTop();
            int childAtPaddingBottom = childAt.getPaddingBottom();

            if (avaialbeWidth < (childAtMeasuredWidth + paddingLeft)) { // 换行
                avaialbeWidth = viewWidth;
                currentWidth = 0;
                // 主要是高度的处理
                currentHeight += childAtMeasuredHeight + childAt.getPaddingTop();
            }

            childAt.layout(paddingLeft + childPaddingLeft + currentWidth,
                    currentHeight + paddingTop + childAtPaddingTop ,
                    paddingLeft + childPaddingLeft + currentWidth + childAtMeasuredWidth,
                    currentHeight + paddingTop + childAtMeasuredHeight + childAtPaddingBottom );

            currentWidth += childPaddingLeft + childAtMeasuredWidth;
            avaialbeWidth = viewWidth - currentWidth;
        }
    }

    // 测量宽度的方法
    private int measureWidth(int widthMeasureSpec) {
        int result = 200;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); // 宽度的测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec); // 宽度
        if (widthMode == MeasureSpec.EXACTLY) { // 如果宽度的测量模式 == 精确值模式(Match_Parent || 指定宽度(100dp) )
            result = widthSize; // 等于测量的宽度
        } else if (widthMode == MeasureSpec.AT_MOST) { // 测量模式 == Wrap_content
            result = Math.min(result, widthSize); // 比对一下，为什么
        }
        return result;
    }

    // 测量高度的方法
    private int measureHeight(int widthMeasureSpec, int heightMeasureSpec) {
        // 要做测量，如果AutoLine 为 wrap_content 时候,他的高度由字孩子确定，要做测量
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int currentWidth = 0; // 已经使用的宽度
        int avaiableWidth = 0; // 剩余的宽度

        int currentHeight = 0; // 当前行中最高的高度
        int totalHeight = 0;
        int avaiableHeight = 0 ;

        avaiableWidth = widthSize;

        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt == null) {
                continue;
            }
            if (childAt.getVisibility() == View.GONE) {
                continue;
            }

            int childAtMeasuredWidth = childAt.getMeasuredWidth();
            int childAtMeasuredHeight = childAt.getMeasuredHeight();

            int childAtPaddingLeft = childAt.getPaddingLeft();
            int childAtPaddingTop = childAt.getPaddingTop();
            int childAtPaddingBottom = childAt.getPaddingBottom();
            int childAtPaddingRight = childAt.getPaddingRight();

            // 判断是否换行
            if (avaiableWidth < childAtMeasuredWidth) { // 换行
                avaiableWidth = widthSize;
                currentWidth = 0;
                totalHeight += avaiableHeight;
            }  // 不换行,不能加载FALSE，因为你换行了，也要计算行中的宽高

            currentHeight = childAtMeasuredHeight + childAtPaddingTop + childAtPaddingBottom;
            currentWidth += childAtMeasuredWidth + childAtPaddingLeft + childAtPaddingRight;
            avaiableWidth = widthSize - currentWidth;
            avaiableHeight = currentHeight;
            totalHeight = Math.max(totalHeight, avaiableHeight);
                /*currentHeight = Math.max(childAtMeasuredHeight, currentHeight);
                totalHeight = Math.max(currentHeight, totalHeight);*/

        }

        int result = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 宽度的测量模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 宽度
        if (heightMode == MeasureSpec.EXACTLY) { // 如果宽度的测量模式 == 精确值模式(Match_Parent || 指定宽度(100dp) )
            result = heightSize; // 等于测量的宽度
        } else if (heightMode == MeasureSpec.AT_MOST) { // 测量模式 == Wrap_content
            result = totalHeight;
            Log.i("mearusrHeight", String.valueOf(heightSize) + " ---- " + result); // mearusrHeight: 1572
            // result = Math.min(result, heightSize); // 比对一下，为什么
        }
        return result;
    }
}

