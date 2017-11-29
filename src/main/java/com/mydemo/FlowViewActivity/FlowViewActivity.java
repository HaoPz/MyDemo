package com.mydemo.FlowViewActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/11/29.
 */

public class FlowViewActivity extends BaseActivity {

    private AutoLineView mAutoLineView;
    public ArrayList<String> stringList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_view);
        mAutoLineView = (AutoLineView) findViewById(R.id.autoLineView);

        makeData();

        for (String contnet : stringList) {
            TextView textView = new TextView(FlowViewActivity.this);
            textView.setBackgroundResource(R.drawable.flow_item_bg); // TextView 背景样式
            textView.setText(contnet);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextSize(8f);

            mAutoLineView.addView(textView);
        }

    }

    private void makeData() {
        stringList = new ArrayList<>();
        stringList.add("裤子");
        stringList.add("自动洗碗机");
        stringList.add("休闲鞋");
        stringList.add("Android群英传");
        stringList.add("人民日报");
        stringList.add("戴尔笔记本 i5 固态硬盘 12G 内存");
        stringList.add("Lol总决赛门票");
        stringList.add("烧水壶");
        stringList.add("京东第一节茅台文化节日");
        stringList.add("迪卡侬店铺");
        stringList.add("戴森");
        stringList.add("瑜伽垫");
        stringList.add("Diy主机");
        stringList.add("美的电暖机");
    }
}
