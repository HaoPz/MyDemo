package com.mydemo.CountDownTimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/28.
 */

public class CountDownTimerDemo extends BaseActivity {

    private Button textView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);
        textView = (Button) findViewById(R.id.getVertify);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null)
                    countDownTimer.start();
            }
        });


        countDownTimer = new CountDownTimer(1000 * 30, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setClickable(false);
                textView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                textView.setClickable(true);
                textView.setText("获取验证码");
            }
        };
    }
}
