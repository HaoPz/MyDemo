package com.mydemo.Retrofit;

import android.content.Context;
import android.util.Log;

import com.mydemo.Utils.ToastUtils;

/**
 * Created by HaoPz on 2017/12/14.
 */

public class RetrofitPostTestBean {

    /**
     * code : 0
     * expire : 31536000
     * userId : 64
     * token : f6b077d7-32e1-421c-815a-8f360030eaea
     */

    private int code;
    private int expire;
    private int userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void show(Context c) {
        ToastUtils.showShort(c, token);
        Log.i("***********", "code:" + code);
        Log.i("***********", "expire:" + expire);
        Log.i("***********", "userId:" + userId);
        Log.i("***********", "token:" + token);
    }
}
