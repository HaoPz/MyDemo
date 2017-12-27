package com.mydemo.ActivityTransitions;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/27.
 */

public class ActivityC extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }
}
