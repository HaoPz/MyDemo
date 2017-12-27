package com.mydemo.IndexList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HaoPz on 2017/12/27.
 */

public class IndexView extends View {

    private Paint txtPaint;
    private int measuredHeight;
    private int measuredWidth;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        txtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        txtPaint.setTextSize(36);
        txtPaint.setColor(Color.parseColor("#949494"));
    }

    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        Log.i("*************", getMeasuredHeight() + "");
        measuredHeight = (int) (getMeasuredHeight() * 1.0f / LETTERS.length);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            int letterX = (int) (measuredWidth * 0.5f - txtPaint.measureText(letter) * 0.5f);
            // int letterY = (int) (measuredHeight * 0.5f - txtPaint.measureText(letter) * 0.5f);
            canvas.drawText(letter, letterX, measuredHeight * (i + 1), txtPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("**********", "按下事件开始");
                float y = event.getY(); // 点击的高度
                int clickPosition = (int) (y / measuredHeight);
                if (clickPosition >= LETTERS.length) {
                    onIndexClickListener.onIndesClickListener(clickPosition, LETTERS[LETTERS.length - 1]);
                } else if (clickPosition < 0) {
                    onIndexClickListener.onIndesClickListener(clickPosition, LETTERS[0]);
                } else {
                    onIndexClickListener.onIndesClickListener(clickPosition, LETTERS[clickPosition]);
                }
                // onIndexClickListener.onIndesClickListener(clickPosition, LETTERS[clickPosition]);
                Log.i("**********", "按下事件结束");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("**********", "移动事件开始");
                float moveY = event.getY(); // 点击的高度
                int movePosition = (int) (moveY / measuredHeight);
                if (movePosition >= LETTERS.length) {
                    onIndexClickListener.onMoveClickListener(movePosition, LETTERS[LETTERS.length - 1]);
                } else if (movePosition < 0) {
                    onIndexClickListener.onMoveClickListener(movePosition, LETTERS[0]);
                } else {
                    onIndexClickListener.onMoveClickListener(movePosition, LETTERS[movePosition]);
                }
                // onIndexClickListener.onMoveClickListener(movePosition, LETTERS[movePosition]);

                Log.i("**********", "移动事件结束");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("**********", "抬起事件");
                float upY = event.getY(); // 点击的高度
                int upPosition = (int) (upY / measuredHeight);
                if (upPosition >= LETTERS.length) {
                    onIndexClickListener.onUpClickListener(upPosition, LETTERS[LETTERS.length - 1]);
                } else if (upPosition < 0) {
                    onIndexClickListener.onUpClickListener(upPosition, LETTERS[0]);
                } else {
                    onIndexClickListener.onUpClickListener(upPosition, LETTERS[upPosition]);
                }
                // onIndexClickListener.onUpClickListener(upPosition, LETTERS[upPosition]);

                break;
        }
        return true;
    }


    // 接口
    public OnIndexClickListener onIndexClickListener;

    public void setOnIndexClickListener(OnIndexClickListener on) {
        this.onIndexClickListener = on;
    }

    protected interface OnIndexClickListener {
        void onIndesClickListener(int position, String indexString);

        void onMoveClickListener(int position, String indexString);// 滑动

        void onUpClickListener(int position, String indexString);// 滑动
    }
}
