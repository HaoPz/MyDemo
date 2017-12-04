package com.mydemo.OkhttpUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mydemo.Base.BaseActivity;
import com.mydemo.OkhttpUtils.interfacee.OkHttpCallBackJson;

/**
 * Created by HaoPz on 2017/12/4.
 */

public class TestOkhttp extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OkHttpUtil.getInstanc().get("", "https://www.baidu.com", null, new OkHttpCallBackJson() {
            @Override
            public void onFail(String failMessage) {
                Log.i("*********", failMessage);
            }

            @Override
            public void onSuccess(String successMessage) {
                Log.i("*********", successMessage);
            }
        });
    }
}
