package com.mydemo.OkhttpUtils.interfacee;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by HaoPz on 2017/12/4.
 */

public class MyCallBackImpl implements Callback {
    public Handler handler;
    public OkHttpCallBackJson okHttpCallBack;

    public MyCallBackImpl(OkHttpCallBackJson okHttpCallBack) {
        handler = new Handler();
        this.okHttpCallBack = okHttpCallBack;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        okHttpCallBack.onFail(e.getMessage().toString());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            final String string = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    okHttpCallBack.onSuccess(string);
                }
            });
        } else {
            okHttpCallBack.onFail(response.message().toString());
        }
    }
}
