package com.mydemo.VerticalScrollText;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.util.ArrayList;

/**
 * 可以上下滚动的TextView
 * Created by HaoPz on 2017/12/11.
 */

public class VerticalScrollTextActivity extends BaseActivity {
    TextSwitcher textSwitcher;
    private ArrayList<String> arrayList = new ArrayList<>();
    private boolean isLoop = true;
    public int current = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // //设置允许过度动画，一定要在setContentView之前调用
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Explode());

        setContentView(R.layout.activity_vertical_scroll_text);
        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        arrayList.add("测试1");
        arrayList.add("测试2");
        arrayList.add("测试3");
        arrayList.add("测试4");
        arrayList.add("测试5");
        arrayList.add("测试6");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {
                    SystemClock.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textSwitcher.setText(arrayList.get(current));
                            Log.i("****cureent:", "main");
                        }
                    });
                    current++;
                    Log.i("***i", current + "");
                    if (current >= arrayList.size()) {
                        current = 0;
                    }
                }
            }
        }).start();

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView mTextView = new TextView(VerticalScrollTextActivity.this);
                mTextView.setTextColor(Color.RED);
                mTextView.setTextSize(14);
                mTextView.setEllipsize(TextUtils.TruncateAt.END);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                mTextView.setLayoutParams(layoutParams);
                return mTextView;
            }
        });

        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (current){
                    case 0:Log.i("*****", "0"); break;
                    case 1:Log.i("*****", "1");break;
                    case 2:Log.i("*****", "2");break;
                    case 3:Log.i("*****", "3");break;
                    case 4:Log.i("*****", "4");break;
                    case 5:Log.i("*****", "5");break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        isLoop = false ;
    }
}
