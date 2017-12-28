package com.mydemo.TextSwitch;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/12/28.
 */

public class TextSwitchDemo extends BaseActivity {
    private ArrayList<String> arrayList = new ArrayList<>();
    private TextSwitcher textSwitcher;
    private boolean isLoop = true;
    private int current = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_switch);

        arrayList.add("尾号6551,成功借款1100元,申请至放款耗时5分钟");
        arrayList.add("尾号1947,成功借款22100元,申请至放款耗时5分钟");
        arrayList.add("尾号3899,成功借款100元,申请至放款耗时5分钟");
        arrayList.add("尾号6412,成功借款500元,申请至放款耗时5分钟");
        arrayList.add("尾号9867,成功借款80000元,申请至放款耗时5分钟");
        arrayList.add("尾号1270,成功借款6600元,申请至放款耗时5分钟");

        textSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView mTextView = new TextView(TextSwitchDemo.this);
                mTextView.setTextColor(Color.parseColor("#7E828C"));
                mTextView.setGravity(Gravity.CENTER_VERTICAL);
                mTextView.setTextSize(14);
                mTextView.setEllipsize(TextUtils.TruncateAt.END);
                mTextView.setLines(1);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                mTextView.setLayoutParams(layoutParams);
                return mTextView;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textSwitcher.setText(arrayList.get(current));
                        }
                    });
                    SystemClock.sleep(2000);
                    current++;
                    Log.i("***i", current + "");
                    if (current >= arrayList.size()) {
                        current = 0;
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLoop = false;
    }
}
