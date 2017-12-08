package com.mydemo.ActivityTransitions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Window;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/8.
 */

public class ActivityB extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //设置允许过度动画，一定要在setContentView之前调用
        int transitions = getIntent().getIntExtra("transitions", -1);
        switch (transitions){
            case 0 : // explode 爆炸
                getWindow().setEnterTransition(new Explode());
                getWindow().setExitTransition(new Explode());
                break;
            case 1 : // fade 褪色
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Fade());
                break;
            case 2 : // Slide 滑动 滑行
                getWindow().setEnterTransition(new Slide());
                getWindow().setExitTransition(new Slide());
                break;
            case 3 :break;
        }
        setContentView(R.layout.activity_b);
    }
}
