package com.mydemo.ViewPager;

import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/12/21.
 */

public class ViewPager3DActivity extends BaseActivity {
    private ViewPager mViewPager3D;
    private ViewPager viewPager3DZoomOutPageTransformer;
    private ViewPager viewPager3DDepthPageTransformer;
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
        mViewPager3D.setPageTransformer(true, new MyTransformation());

        viewPager3DZoomOutPageTransformer = (ViewPager) findViewById(R.id.viewPager3DZoomOutPageTransformer);
        ViewPager3DAdapter viewPager3DAdapter1 = new ViewPager3DAdapter(imageList, ViewPager3DActivity.this);
        viewPager3DZoomOutPageTransformer.setAdapter(viewPager3DAdapter1);
        viewPager3DZoomOutPageTransformer.setPageTransformer(true, new ZoomOutPageTransformer());

        viewPager3DDepthPageTransformer = (ViewPager) findViewById(R.id.viewPager3DDepthPageTransformer);
        ViewPager3DAdapter viewPager3DAdapter2 = new ViewPager3DAdapter(imageList, ViewPager3DActivity.this);
        viewPager3DDepthPageTransformer.setAdapter(viewPager3DAdapter2);
        viewPager3DDepthPageTransformer.setPageTransformer(true, new DepthPageTransformer());

    }

    public class MyTransformation implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.5f;// 最小缩放
        private static final float MIN_ALPHA = 0.5f; // 最小透明度
        private static final float MAX_ROTATE = 30; // 最大旋转
        private Camera camera = new Camera();

        @Override
        public void transformPage(View page, float position) {
            float centerX = page.getWidth() / 2;
            float centerY = page.getHeight() / 2;
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float rotate = 20 * Math.abs(position);
            if (position < -1) {

            } else if (position < 0) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                // page.setRotationY(rotate);
            } else if (position >= 0 && position < 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                // page.setRotationY(-rotate);
            } else if (position >= 1) {
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                // page.setRotationY(-rotate);
            }
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
