package com.mydemo.DiyProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mydemo.R;
import com.mydemo.Utils.DensityUtil;

/**
 * Created by HaoPz on 2017/11/30.
 */

public class MyProgressBar extends View {
    /**
     * 当前进度
     */
    private int currentProgress;

    /**
     * 总进度
     */
    private int maxProgress;

    /**
     * 左侧进度条画笔
     */
    private Paint leftProgressPaint;

    /**
     * 背景进度条画笔
     */
    private Paint bgProgressPaint;

    /**
     * 右侧进度条画笔
     */
    private Paint rightProgressPaint;

    /**
     * 文字进度条画笔
     */
    private Paint textProgressPaint;

    /**
     * 显示的进度数字
     */
    private int currentNumber;

    /**
     * 显示的进度数字单位
     */
    private String numberUnit = "%";

    /**
     * 显示的进度数字 偏移量
     */
    private int offsetText;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myPorgressBar);

        currentProgress = typedArray.getInteger(R.styleable.myPorgressBar_currenProgress, 0);
        maxProgress = typedArray.getInteger(R.styleable.myPorgressBar_maxProgress, 0);
        currentNumber = typedArray.getInteger(R.styleable.myPorgressBar_currentNumber, 0);
        offsetText = typedArray.getInteger(R.styleable.myPorgressBar_offsetText, 0);

        typedArray.recycle(); // mResources.mTypedArrayPool.release(this); 供稍后调用者重新使用

        initPaint();
    }

    /**
     * 初始化 画笔
     */
    private void initPaint() {
        leftProgressPaint = new Paint();
        leftProgressPaint.setColor(Color.RED);
        leftProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        leftProgressPaint.setStrokeWidth(4);
        leftProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        rightProgressPaint = new Paint();
        rightProgressPaint.setColor(Color.GRAY);
        rightProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        rightProgressPaint.setStrokeWidth(4);
        rightProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        bgProgressPaint = new Paint();
        bgProgressPaint.setColor(Color.YELLOW);
        bgProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bgProgressPaint.setStrokeWidth(4);
        bgProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);


        textProgressPaint = new Paint();
        textProgressPaint.setColor(Color.BLACK);
        textProgressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textProgressPaint.setStrokeWidth(1);
        textProgressPaint.setTextSize(DensityUtil.dip2px(getContext(), 10));
        textProgressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getMaxProgress() <= 0) {
            throw new RuntimeException("maxProgress can not be less than or equal to 0");
        }
        super.onDraw(canvas);

        RectF bgRecf = new RectF(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + getMeasuredWidth(), getPaddingTop() + getMeasuredHeight());

        canvas.drawRect(bgRecf, bgProgressPaint); // 画背景

        onDrawLeftRect(canvas);

    }

    private void onDrawLeftRect(Canvas canvas) {
        int leftRectWidth = 0;

        RectF rightRecF = new RectF(leftRectWidth, getPaddingTop(), getMeasuredWidth(), getPaddingTop() + getMeasuredHeight());
        canvas.drawRect(rightRecF, rightProgressPaint);

        if (currentProgress != 0) {
            /**
             *  左侧进度宽度 = 当前进度 - 总进度 * 控件在屏幕宽度
             * */
            leftRectWidth = getCurrentProgress() * (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / getMaxProgress();
            RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), leftRectWidth, getPaddingTop() + getMeasuredHeight());
            canvas.drawRect(rectF, leftProgressPaint);
        }

        Rect textBounds = new Rect(); // 测量文字内容宽高
        textProgressPaint.getTextBounds(currentNumber + numberUnit, 0, (currentNumber + numberUnit).length(), textBounds);
        int mTextWidth = textBounds.width();

        if (getCurrentProgress() >= 94) {
            canvas.drawText(String.valueOf(currentNumber + numberUnit), getMeasuredWidth() - mTextWidth, getMeasuredHeight(), textProgressPaint);
        } else {
            canvas.drawText(String.valueOf(currentNumber + numberUnit), leftRectWidth + offsetText, getMeasuredHeight(), textProgressPaint);
        }

    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getOffsetText() {
        return offsetText;
    }

    public void setOffsetText(int offsetText) {
        this.offsetText = offsetText;
    }

    private int measureHeight(int heightMeasureSpec) {
        int returnSize = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            returnSize = size;
        } else {
            if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
                returnSize = Math.min(15, size);
            }
        }
        return returnSize;
    }

    private int measureWidth(int widthMeasureSpec) {
        int returnSize = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            returnSize = size;
        } else {
            if (mode == MeasureSpec.AT_MOST) {
                returnSize = Math.min(200, size);
            }
        }
        return returnSize;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        currentNumber = (int) (currentProgress * 100 / getMaxProgress());
        invalidate();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
