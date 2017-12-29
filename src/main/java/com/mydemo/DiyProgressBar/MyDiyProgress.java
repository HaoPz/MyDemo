package com.mydemo.DiyProgressBar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;

/**
 * Created by HaoPz on 2017/12/28.
 */

public class MyDiyProgress extends View {

    private Paint arcPaint;
    private Paint mPaint;

    /**
     *从代码中可以看到，我们只需要改变ofFloat()的第二个参数的值就可以实现对应的动画；
     * 那么问题来了，我们怎么知道第二个参数的值是啥呢？
     *
     * TextView控件有rotation这个属性吗？
     * 没有，不光TextView没有，连它的父类View中也没有这个属性。
     * 那它是怎么来改变这个值的呢？其实，ObjectAnimator做动画，并不是根据控件xml中的属性来改变的，而是通过指定属性所对应的set方法来改变的。
     * 比如，我们上面指定的改变rotation的属性值，
     * ObjectAnimator在做动画时就会到指定控件（TextView）中去找对应的setRotation()方法来改变控件中对应的值。
     * 同样的道理，当我们在最开始的示例代码中，指定改变”alpha”属性值的时候，ObjectAnimator也会到TextView中去找对应的setAlpha()方法。
     * 那TextView中都有这些方法吗，有的，这些方法都是从View中继承过来的，在View中有关动画，总共有下面几组set方法：
     *
     * 可以看到在View中已经实现了有关alpha,rotaion,translate,scale相关的set方法。所以我们在构造ObjectAnimator时可以直接使用。
     在开始逐个看这些函数的使用方法前，我们先做一个总结：
     1、要使用ObjectAnimator来构造对画，要操作的控件中，必须存在对应的属性的set方法
     2、setter 方法的命名必须以骆驼拼写法命名，即set后每个单词首字母大写，其余字母小写，即类似于setPropertyName所对应的属性为propertyName
     下面我们就来看一下上面中各个方法的使用方法及作用。
     有关alpha的用法，上面已经讲过了，下面我们来看看其它的
     * */

    /**
     * 画笔的宽度
     */
    private int paintWidth;

    /**
     * 要画的弧度
     */
    private float currentNumber = 0;

    public MyDiyProgress(Context context) {
        this(context, null);
    }

    public MyDiyProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDiyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paintWidth = 30;
        arcPaint = new Paint();
        arcPaint.setColor(Color.GRAY);
        arcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStrokeWidth(paintWidth);
        arcPaint.setStyle(Paint.Style.STROKE);

        paintWidth = 30;
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeCap(Paint.Cap.ROUND);// DrawArc画圆环的时候，如果设置width过大直角的笔头会画得很丑
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 底部弧形
        RectF ov = new RectF(paintWidth, paintWidth,
                getRight() - getLeft() - paintWidth, getBottom() - getTop() - paintWidth);
        canvas.drawArc(ov, -90, 360, false, arcPaint);
        // 显示进度弧度
        canvas.drawArc(ov, -90, currentNumber, false, mPaint);
    }

    /**
     * 赋值+执行动画
     *
     * @param currentNumber 进度 float
     */
    public void dodo(float currentNumber) {
        // this.currentNumber = currentNumber;
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(this, "currentNumber", 0f, currentNumber);
        progressAnimation.setDuration(1000);//动画耗时
        /*
         * AccelerateInterpolator　　　　　                  加速，开始时慢中间加速
         * DecelerateInterpolator　　　 　　                 减速，开始时快然后减速
         * AccelerateDecelerateInterolator　                     先加速后减速，开始结束时慢，中间加速
         * AnticipateInterpolator　　　　　　                 反向 ，先向相反方向改变一段再加速播放
         * AnticipateOvershootInterpolator　                 反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
         * BounceInterpolator　　　　　　　                        跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
         * CycleIinterpolator　　　　　　　　                   循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 *
         * mCycles * Math.PI * input) LinearInterpolator　　　 线性，线性均匀改变
         * OvershottInterpolator　　　　　　                  超越，最后超出目的值然后缓慢改变到目的值
         * TimeInterpolator　　　　　　　　　                        一个接口，允许你自定义interpolator，以上几个都是实现了这个接口
         */
        progressAnimation.setInterpolator(new AccelerateInterpolator());

        animation.playTogether(progressAnimation);
        animation.start();
    }

    public void setCurrentNumber(float currentNumber) {
        this.currentNumber = currentNumber;
        invalidate();
    }

    public void setPaintWidth(int paintWidth) {
        this.paintWidth = paintWidth;
    }

}
