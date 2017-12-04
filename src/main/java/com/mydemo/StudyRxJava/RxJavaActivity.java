package com.mydemo.StudyRxJava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/1.
 */

public class RxJavaActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        findViewById(R.id.baseRxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent baseRxJava = new Intent(RxJavaActivity.this, BaseRxjavaActivity.class);
                startActivity(baseRxJava);
            }
        });
    }
}
