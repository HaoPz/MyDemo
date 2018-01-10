package com.mydemo.MaterialDesign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2018/1/10.
 * Android 5.0  Material Design 样式学习
 */

public class MaterialDesignActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#4FC3F7"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materil_design);

        initView();

    }

    private void initView() {
        findViewById(R.id.bottonNavigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bottomNavigationIntent = new Intent(MaterialDesignActivity.this, BottomNavigationActivity.class);
                startActivity(bottomNavigationIntent);
            }
        });

        findViewById(R.id.switchCompat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchCompatIntent = new Intent(MaterialDesignActivity.this, SwitchCompatActivity.class);
                startActivity(switchCompatIntent);
            }
        });
    }
}
