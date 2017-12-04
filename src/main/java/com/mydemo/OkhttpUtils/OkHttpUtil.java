package com.mydemo.OkhttpUtils;

import com.mydemo.OkhttpUtils.interfacee.MyCallBackImpl;
import com.mydemo.OkhttpUtils.interfacee.OkHttpCallBackJson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * okhttp 请求封装
 * Created by HaoPz on 2017/12/4.
 */

public class OkHttpUtil {
    private static OkHttpClient okHttpClient;
    private static OkHttpUtil OkHttpUtil;

    private OkHttpUtil() {}

    public static OkHttpUtil getInstanc() {
        if(OkHttpUtil == null){
            OkHttpUtil = new OkHttpUtil();
        }
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return OkHttpUtil;
    }

    public void get(String headValue, String url, HashMap<String, Object> hashMap, final OkHttpCallBackJson okHttpCallBack) {
        Request.Builder builder = new Request.Builder();
        /**
         * Get 请求,拼接参数
         * */
        int i = 0;
        if(hashMap != null){
            Set<Map.Entry<String, Object>> entries = hashMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
            for (Map.Entry<String, Object> entry : entries) {
                if(i == 0){
                    url += "?" + entry.getKey() + "=" + entry.getValue();
                }else{
                    url += "&" + entry.getKey() + "=" + entry.getValue();
                }
                i++;
            }
        }

        /**
         * 请求头 以token 为例,具体使用根据实际使用为准
         * */
        final Request request = builder.addHeader("token", headValue)
                .url(url)
                .get().build();


        okHttpClient.newCall(request).enqueue(new MyCallBackImpl(okHttpCallBack));
    }
}
