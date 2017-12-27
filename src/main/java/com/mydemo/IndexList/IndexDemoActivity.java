package com.mydemo.IndexList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/27.
 */

public class IndexDemoActivity extends BaseActivity {
    private IndexView mIndex;
    private RelativeLayout mShowTextLayout;
    private TextView mShowText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    private void initView() {
        mIndex = (IndexView) findViewById(R.id.index);
        mShowTextLayout = (RelativeLayout) findViewById(R.id.showTextLayout);
        mShowText = (TextView) findViewById(R.id.showText);

        mIndex.setOnIndexClickListener(new IndexView.OnIndexClickListener() {
            @Override
            public void onIndesClickListener(int position, String indexString) {
                mShowTextLayout.setVisibility(View.VISIBLE);
                showTextLayout(indexString);
                // Toast.makeText(IndexDemoActivity.this, indexString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMoveClickListener(int position, String indexString) {
                mShowTextLayout.setVisibility(View.VISIBLE);
                showTextLayout(indexString);
                // Toast.makeText(IndexDemoActivity.this, indexString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpClickListener(int position, String indexString) {
                mShowTextLayout.setVisibility(View.GONE);
                // Toast.makeText(IndexDemoActivity.this, indexString, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showTextLayout(String showText) {
        // mShowTextLayout.setVisibility(View.VISIBLE);
        mShowText.setText(showText);
        /*if(mShowTextLayout.getVisibility() == View.GONE){
            mShowTextLayout.setVisibility(View.VISIBLE);
            mShowText.setText(showText);
        }else{
            mShowTextLayout.setVisibility(View.GONE);
        }*/
    }
}
