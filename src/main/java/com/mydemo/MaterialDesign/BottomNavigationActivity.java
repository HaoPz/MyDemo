package com.mydemo.MaterialDesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * bottomNavigation
 * Created by HaoPz on 2018/1/10.
 */

public class BottomNavigationActivity extends BaseActivity {

    private TextView content;
    private BottomNavigationView viewById;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // getWindow().setStatusBarColor(Color.parseColor("#2196F3"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        content = (TextView) findViewById(R.id.content);
        viewById = (BottomNavigationView) findViewById(R.id.bottomNavigationItemView);

        viewById.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String selectedTitle = item.getTitle().toString().trim();
                content.setText(selectedTitle);
                return true;
            }
        });
    }
}
