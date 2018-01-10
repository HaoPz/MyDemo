package com.mydemo.ActivityTransitions;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HaoPz on 2017/12/8.
 */

public class ActivityA extends BaseActivity {


    @BindView(R.id.a1)
    TextView a1;
    @BindView(R.id.a2)
    TextView a2;
    @BindView(R.id.a3)
    TextView a3;
    @BindView(R.id.a4)
    TextView a4;
    @BindView(R.id.transitionIcon)
    ImageView transitionIcon;
    @BindView(R.id.transitionIcon1)
    ImageView transitionIcon1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 想要生效，必须在跳转时候使用
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // //设置允许过度动画，一定要在setContentView之前调用
            getWindow().setExitTransition(new Fade());
        }
        //startActivity(goActivityAIntent,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

        setContentView(R.layout.activity_a);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.a1, R.id.a2, R.id.a3, R.id.a4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.a1:
                Intent intent1 = new Intent(ActivityA.this, ActivityB.class);
                intent1.putExtra("transitions", 1);// explode
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.a2:
                Intent intent2 = new Intent(ActivityA.this, ActivityB.class);
                intent2.putExtra("transitions", 2);// Fade
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.a3:
                Intent intent3 = new Intent(ActivityA.this, ActivityB.class);
                intent3.putExtra("transitions", 3);// Slide
                startActivity(intent3, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.a4: // 共享元素
                Intent intent4 = new Intent(ActivityA.this, ActivityB.class);
                intent4.putExtra("transitions", 4);// explode
                startActivity(intent4, ActivityOptions.makeSceneTransitionAnimation(this, transitionIcon, "hh").toBundle());
                break;
        }
    }

}
