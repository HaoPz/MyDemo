package com.mydemo.LayoutImage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/26.
 */

public class LayoutImageActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_circle_image);
    }
}
