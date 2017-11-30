package com.mydemo.SwipeMenuView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/11/30.
 */

public class SwipeMenuViewActivity extends BaseActivity {
    private SwipeMenuView swipeMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_view);

        swipeMenuView = (SwipeMenuView) findViewById(R.id.swipeMenuView);

        swipeMenuView.setOnSwipeMenuClickListener(new SwipeMenuView.SwipeMenuClickListener() {
            @Override
            public void onContentClickListener() {
                Log.i("*******", "content");
            }

            @Override
            public void onDeleteClickListener() {
                Log.i("*******", "delete");
                swipeMenuView.smoothClose();
            }
        });
    }
}
