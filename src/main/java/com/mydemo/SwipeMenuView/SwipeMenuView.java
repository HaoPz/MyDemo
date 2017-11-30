package com.mydemo.SwipeMenuView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by HaoPz on 2017/11/16.
 * 策划删除
 */

public class SwipeMenuView extends ViewGroup {

    private final String TAG = String.valueOf(getClass().getName()).toUpperCase();

    public SwipeMenuView(Context context) {
        this(context, null);
    }

    public SwipeMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    int rightWeight = 0;

    /**
     * 测量布局和子布局
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setClickable(true);
        int childCount = getChildCount();

        int allHeight = 0; // 侧滑菜单,显示的高度,
        int showWidth = 0;// 侧滑菜单,屏幕显示的宽度
        int allWidth = 0; // 侧滑菜单整体的宽度

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.setClickable(true);
            if (childAt.getVisibility() == View.GONE) {
                continue;
            }

            Log.i("测试在child.measure前获取宽和高", "宽：" + childAt.getMeasuredWidth() + "-高:" + childAt.getMeasuredHeight());

            /*
            ViewGroup 中俩种测试子孩子宽高的方法:
                第一种;
                    measureChild(childAt,widthMeasureSpec, heightMeasureSpec); // 穿进去孩子对象和父亲测量规范
                    final LayoutParams lp = child.getLayoutParams();

                    final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                        mPaddingLeft + mPaddingRight, lp.width);
                    final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                        mPaddingTop + mPaddingBottom, lp.height);

                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                查看源码发现,里面是通过拿到孩子的宽高和对象,然后通过一个方法 getChildMeasureSpec()
                里面需要：
                    父亲测量规范, child 对象 padding , child 对象, 传的是父亲宽测量规范拿到孩子宽度测量规范
                    父亲测量规范, child 对象 padding , child 对象, 传的是父亲高测量规范拿到孩子高度测量规范

            */
            // measureChild(childAt, widthMeasureSpec, heightMeasureSpec); // 实现走的也是 childAt.measure

            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, childAt.getLayoutParams().width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, childAt.getLayoutParams().height);
            childAt.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            // childAt.measure(widthMeasureSpec, heightMeasureSpec); // 里面应该传入孩子自己的测量规范,不应该为父亲测量规范

            Log.i("测试在child.measure后获取宽和高", "宽：" + childAt.getMeasuredWidth() + "-高:" + childAt.getMeasuredHeight());

            int childAtMeasuredHeight = childAt.getMeasuredHeight(); // 子布局的高度
            allHeight = Math.max(childAtMeasuredHeight, allHeight); // 比较一下当前孩子的高度和之前孩子中最大高度，

            int childAtMeasuredWidth = childAt.getMeasuredWidth();
            if (i == 0) {
                showWidth = getMeasuredWidth();// 使用 getMeasuredWidt(), 确保已经测量过了
            } else {
                allWidth += childAtMeasuredWidth;
            }
        }
        Log.i("整体测量完成", "侧滑菜单宽:" + allWidth + "-高度:" + allHeight + "第一个孩子的宽度:" + showWidth);

        rightWeight = allWidth;
        setMeasuredDimension(showWidth, allHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout: " + l + t + r + b);// L;0 T:0 R:1080 B:300
        int childCount = getChildCount();

        int currentLayoutLeft = l;
        int currentLayoutTop = t;
        int currentLayoutRight = r;
        int currentLayoutBottom = b;

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == View.GONE) {
                continue;
            }

            if (i == 0) { // 第一个正常内容
                childAt.layout(currentLayoutLeft, currentLayoutTop, currentLayoutRight, currentLayoutBottom);
            } else {

                int childAtMeasuredHeight = childAt.getMeasuredHeight();
                int childAtMeasuredWidth = childAt.getMeasuredWidth();

                currentLayoutLeft = currentLayoutRight;
                currentLayoutTop = Math.max(currentLayoutTop, childAt.getTop());
                currentLayoutRight = currentLayoutRight + childAtMeasuredWidth;
                currentLayoutBottom = Math.max(currentLayoutBottom, childAtMeasuredHeight);

                Log.i(TAG, "onLayout: currentLayoutLeft" + currentLayoutLeft);
                Log.i(TAG, "onLayout: currentLayoutTop" + currentLayoutTop);
                Log.i(TAG, "onLayout: currentLayoutRight" + currentLayoutRight);
                Log.i(TAG, "onLayout: currentLayoutBottom" + currentLayoutBottom);
                childAt.layout(currentLayoutLeft, currentLayoutTop, currentLayoutRight, currentLayoutBottom);
            }
        }
    }

    // 拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {// 自己处理
        /*boolean onInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onInterceptTouchEvent: down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent: move || up ");
                onInterceptTouchEvent = true;
                break;
        }*/
        return true;
    }

    float downX = 0.0f;
    float downY = 0.0f;

    float lastX = 0.0f;
    float lastY = 0.0f;

    float moveX = 0.0f;
    float moveY = 0.0f;

    boolean isOpen = false; // 删除按钮是否显示

    // 处理事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                Log.i(TAG, "onTouchEvent: " + downX);

                lastX = downX;
                lastY = downY;

                // 添加了点击事件。然后要在这里判断点击的位置
                if (isOpen) {
                    if (downX >= 0 && downX < (getChildAt(0).getMeasuredWidth() - rightWeight)) {
                        s.onContentClickListener();
                    } else {
                        s.onDeleteClickListener();
                    }
                } else {
                    s.onContentClickListener(); // 没有打开侧滑删除按钮一定是内容的点击事件
                }

                return true;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                moveY = event.getRawY();

                float distance = -(moveX - lastX); // 移动的是画布
                Log.i(TAG, "distance: " + distance);
                if (distance <= 0 && isOpen == false) {
                    // 手指往右滑动 && 当前已经关闭了按钮就不做处理
                } else if (distance >= 0 && isOpen == true) {
                    // 手指往左滑动 && 当前已经打开了按钮就不做处理
                } else if (distance <= 0) { // 手指往右滑动
                    // 打开状态
                    if (distance <= 0 && distance >= -rightWeight / 2) {
                        isOpen = true;
                        scrollTo((int) (rightWeight - (-distance)), 0);
                    } else if (distance <= -rightWeight / 2 && distance >= -rightWeight) {
                        isOpen = false;
                        scrollTo((int) (rightWeight - (-distance)), 0);
                    } else {
                        isOpen = false;
                        scrollTo(0, 0);
                    }
                } else if (distance >= rightWeight) {
                    isOpen = true;
                    scrollTo(rightWeight, 0);
                } else {
                    if (distance >= 0 && distance < rightWeight / 2) {
                        isOpen = false;// 手指往左滑动,屏幕向右显示,从不显示到右侧删除按钮 到 一半
                        scrollTo((int) distance, 0);
                    } else if (distance >= rightWeight / 2 && distance < rightWeight) {
                        isOpen = true;// 手手指往左滑动,屏幕向右显示,从右侧删除按钮一半 到 显示全部右侧按钮
                        scrollTo((int) distance, 0);
                    }
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (isOpen) {
                    // 打开动画
                    // openAnim();
                    smoothExpand();
                } else {
                    // 关闭动画
                    // closeAnim();
                    smoothClose();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 平滑展开
     */
    public void smoothExpand() {
        isOpen = true;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getScrollX(), rightWeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo((Integer) animation.getAnimatedValue(), 0);
            }
        });
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.setDuration(300).start();
    }

    /**
     * 平滑关闭
     */
    public void smoothClose() {
        isOpen = false; // 这句话是为了在用户点击了删除按钮后，滑动关闭删除按钮
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getScrollX(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo((Integer) animation.getAnimatedValue(), 0);
            }
        });
        valueAnimator.setInterpolator(new AnticipateInterpolator());
        valueAnimator.setDuration(300).start();
    }


    public SwipeMenuClickListener s;

    public void setOnSwipeMenuClickListener(SwipeMenuClickListener s) {
        this.s = s;
    }

    public interface SwipeMenuClickListener {
        void onContentClickListener();

        void onDeleteClickListener();
    }
}
