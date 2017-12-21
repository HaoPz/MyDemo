package com.mydemo.Retrofit;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by HaoPz on 2017/12/14.
 */

public interface RetrofitHttpPostService {
    @POST("api/login")
    Call<RetrofitPostTestBean> postLogin(@Body Login number);

    @POST("api/login")
    Call<RetrofitPostTestBean> postLogin2(@Field("mobile") String number, @Field("password") String password);

    @POST("api/login")
    Observable<RetrofitPostTestBean> postLogin3(@Body RequestBody number);

}
