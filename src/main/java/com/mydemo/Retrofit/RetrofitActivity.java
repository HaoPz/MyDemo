package com.mydemo.Retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.mydemo.Base.BaseActivity;
import com.mydemo.R;
import com.mydemo.Utils.ToastUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 使用
 * Created by HaoPz on 2017/12/13.
 */

public class RetrofitActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        findViewById(R.id.retrofitGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRetrofitGet();
            }
        });

        findViewById(R.id.retrofitPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRetrofitPost();
            }
        });

        findViewById(R.id.retrofitAndRxJava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRxAndroidAndRetrofit();
            }
        });
    }

    private void goRxAndroidAndRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder newBuilder = chain.request().newBuilder().addHeader("Content-Type", "application/json");
                return chain.proceed(newBuilder.build());
            }
        });
        OkHttpClient build = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://60.205.231.116:80/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // 针对rxjava2.x
                .client(build)
                .build();
        RetrofitHttpPostService retrofitHttpPostService = retrofit.create(RetrofitHttpPostService.class);


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobile", "15275157828");
        hashMap.put("password", "asd123"); // 不是Json  {password=asd123, mobile=15275157828}

        String s = new Gson().toJson(hashMap); // "{"password":"asd123","mobile":"15275157828"}"

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), s);

        retrofitHttpPostService.postLogin3(requestBody)
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())
                .subscribe(new Observer<RetrofitPostTestBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull RetrofitPostTestBean retrofitPostTestBean) {
                Log.i("********", retrofitPostTestBean.getToken());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("********", e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void startRetrofitPost() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request build = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .build();
                        return chain.proceed(build);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://60.205.231.116:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        RetrofitHttpPostService retrofitHttpPostService = retrofit.create(RetrofitHttpPostService.class);

        Login login = new Login();
        login.mobile = "15275157828";
        login.password = "asd123";

        retrofitHttpPostService.postLogin(login).enqueue(new Callback<RetrofitPostTestBean>() {
            @Override
            public void onResponse(Call<RetrofitPostTestBean> call, Response<RetrofitPostTestBean> response) {
                Log.i("**", "issuccess:" + response.isSuccessful());
                response.body().show(RetrofitActivity.this);
            }

            @Override
            public void onFailure(Call<RetrofitPostTestBean> call, Throwable t) {
                ToastUtils.showShort(RetrofitActivity.this, t.toString());
                Log.i("*****", t.toString());

            }
        });
    }

    private void startRetrofitGet() {

        // GsonConverterFactory: 代表着转换器--Gson
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gc.ditu.aliyun.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitHttpGetService retrofitHttpService = retrofit.create(RetrofitHttpGetService.class);
        Call<RetrofitTestBean> retrofitTestBean = retrofitHttpService.getJW("济南市");
        retrofitTestBean.enqueue(new Callback<RetrofitTestBean>() {
            @Override
            public void onResponse(Call<RetrofitTestBean> call, Response<RetrofitTestBean> response) {
                RetrofitTestBean body = response.body();
                body.print();
            }

            @Override
            public void onFailure(Call<RetrofitTestBean> call, Throwable t) {
                Log.i("*******", t.toString());
            }
        });


    }
}
