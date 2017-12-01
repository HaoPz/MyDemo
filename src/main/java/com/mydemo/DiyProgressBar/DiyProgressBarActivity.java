package com.mydemo.DiyProgressBar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/11/30.
 */

public class DiyProgressBarActivity extends BaseActivity {

    private MyProgressBar diyMyProgress;

    private int currentProgress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        diyMyProgress = (MyProgressBar) findViewById(R.id.diyMyProgress);
        diyMyProgress.setMaxProgress(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < 100) {
                    synchronized (DiyProgressBarActivity.this){
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
        }).start();
    }
}
