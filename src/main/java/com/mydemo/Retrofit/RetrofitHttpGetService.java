package com.mydemo.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HaoPz on 2017/12/13.
 */

public interface RetrofitHttpGetService {
    @GET("geocoding")
    Call<RetrofitTestBean> getJW(@Query("a") String a);
}
