package com.mydemo.StudyRxJava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by HaoPz on 2017/12/1.
 * Rxjava 入门教程
 */

public class BaseRxjavaActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_rxjava);


        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.i("***","当前线程及名称："+Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i("***","当前线程及名称："+Thread.currentThread().getName());
                Log.i("***","拿到了观察者的内容"+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
