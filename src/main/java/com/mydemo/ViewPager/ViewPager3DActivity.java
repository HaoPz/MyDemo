package com.mydemo.ViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/12/21.
 */

public class ViewPager3DActivity extends BaseActivity {
    private ViewPager mViewPager3D;
    private ArrayList<Integer> imageList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d_viewpager);


        initView();
    }

    private void initView() {
        imageList.add(R.drawable.taile1);
        imageList.add(R.drawable.taile2);
        imageList.add(R.drawable.taile3);
        imageList.add(R.drawable.taile4);
        imageList.add(R.drawable.taile5);

        mViewPager3D = (ViewPager) findViewById(R.id.viewPager3D);

        ViewPager3DAdapter viewPager3DAdapter = new ViewPager3DAdapter(imageList, ViewPager3DActivity.this);

        mViewPager3D.setAdapter(viewPager3DAdapter);
    }
}
