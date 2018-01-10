package com.mydemo.MaterialDesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2018/1/10.
 */

public class SwitchCompatActivity extends BaseActivity {

    private SwitchCompat switchCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_compat);

        switchCompat = (SwitchCompat) findViewById(R.id.switchCompat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("当前状态:", "" + isChecked);
            }
        });
    }
}
