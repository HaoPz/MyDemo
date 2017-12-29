package com.mydemo.DiyProgressBar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/11/30.
 */

public class DiyProgressBarActivity extends BaseActivity {

    private MyProgressBar diyMyProgress;
    private MyDiyProgress diyDialog;

    private int currentProgress = 0;
    private boolean isshow = true;

    private MyCircle myCircle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        myCircle = (MyCircle) findViewById(R.id.myCircle);
        myCircle.setVisibility(View.GONE);
        // myCircle.dodo(70f, 70f);


        diyDialog = (MyDiyProgress) findViewById(R.id.diyDialog);
        setDate();

        diyMyProgress = (MyProgressBar) findViewById(R.id.diyMyProgress);
        diyMyProgress.setMaxProgress(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < 100) {
                    synchronized (DiyProgressBarActivity.this) {
                        try {
                            Thread.sleep(100);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("**************", String.valueOf(currentProgress));
                                    diyMyProgress.setCurrentProgress(currentProgress);
                                    currentProgress += 1;
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    int i = 0;

    private void setDate() {
        diyDialog.dodo(270);
        /*final int number = (int) (0.98 * 360);  // 最好给百分比，然后那是在总体 360 所占的比率
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isshow && i <= number) {
                    synchronized (DiyProgressBarActivity.this) {
                        try {
                            Thread.sleep(30);
                            Log.i("****", i + "");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    diyDialog.setCurrentNumber(i);
                                }
                            });
                            i += 2;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isshow = false;
    }
}
