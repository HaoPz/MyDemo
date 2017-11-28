package com.mydemo.StickyHeadActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mydemo.Base.BaseActivity;
import com.mydemo.R;
import com.mydemo.Utils.AssetsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaoPz on 2017/11/28.
 * RecyclerView 粘性头布局
 */

public class StickyHeadActivity extends BaseActivity {
    private final String TAG = this.getClass().getName();
    private ArrayList<AddressBean> arrayList = new ArrayList<>();
    private TextView headTextView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CityBean cityBean;
    private List<CityBean.CitiesBean> cities;

    private String currentHead = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_head);

        makeData();

        headTextView = (TextView) findViewById(R.id.headTextView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(StickyHeadActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new HeadRecyclerAdapter(arrayList));

        cities = cityBean.getCities();

        // 数据整合:两级数据列表,数据整合成一个结构，因为 RecyclerView 里面数据源是惟一的,也可以
        // 不动数据源,item 显示的时候通过 ViewGroup add 进去,
        for (int i = 0; i < cities.size(); i++) {
            CityBean.CitiesBean citiesBean = cities.get(i);
            AddressBean addressBean = new AddressBean("Head", citiesBean.getAreaName(), citiesBean.getAreaId());
            arrayList.add(addressBean);

            List<CityBean.CitiesBean.CountiesBean> counties = citiesBean.getCounties();
            if (counties.size() > 0) {
                for (int j = 0; j < counties.size(); j++) {
                    AddressBean addressBean1 = new AddressBean("Content", counties.get(j).getAreaName(), counties.get(j).getAreaId());
                    arrayList.add(addressBean1);
                }
            }
        }

        headTextView.setText(arrayList.get(0).getAreaName()); // 设置第一个显示的头
        currentHead = arrayList.get(0).getAreaName();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                String headContent = arrayList.get(firstVisibleItemPosition).getAreaName();

                /**
                 * 如果是头布局,那就显示出来,
                 *
                 * 如果是内容,拿到源数据源结构,遍历查询一下当前二级数据在源数据源中的位置,
                 * 然后在源数据源中获取当前二级数据源的父结构----一级数据源,然后判断当前显示的
                 * Head 和 一级数据源是否一样,不一样改变
                 * */
                if (arrayList.get(firstVisibleItemPosition).getType().contains("Head")) {
                    headTextView.setText(headContent);
                    currentHead = headContent;
                } else if (arrayList.get(firstVisibleItemPosition).getType().contains("Content")) {
                    for (int i = 0; i < cities.size(); i++) {
                        for (int k = 0; k < cities.get(i).getCounties().size(); k++) {
                            if (arrayList.get(firstVisibleItemPosition).getAreaName().equals(cities.get(i).getCounties().get(k).getAreaName())) {
                                if (!currentHead.equals(cities.get(i).getAreaName())) {
                                    currentHead = cities.get(i).getAreaName();
                                    Log.i(TAG, "onScrolled: "+k);
                                }
                            }
                        }
                    }
                    headTextView.setText(currentHead);
                }
            }
        });

    }

    private class HeadRecyclerAdapter extends RecyclerView.Adapter<HeadRecyclerAdapter.BaseViewHolder> {
        private static final int ITEM_HEAD = 1; // 头布局
        private static final int ITEM_CONTENT = 2; // 内容布局
        private ArrayList<AddressBean> arrayList;
        private int item_head = R.layout.item_head;
        private int item_content = R.layout.item_content;

        public HeadRecyclerAdapter(ArrayList<AddressBean> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public int getItemViewType(int position) {
            if (arrayList.get(position).getType().equals("Head")) {
                return ITEM_HEAD;
            }
            return ITEM_CONTENT;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = null;
            switch (viewType) {
                case ITEM_HEAD:
                    View headView = LayoutInflater.from(getApplicationContext()).inflate(item_head, parent, false);
                    viewHolder = new HeadViewHolder(headView);
                    break;
                case ITEM_CONTENT:
                    View contentView = LayoutInflater.from(getApplicationContext()).inflate(item_content, parent, false);
                    viewHolder = new ContentViewHolder(contentView);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case ITEM_HEAD:
                    ((HeadViewHolder) holder).headTextView.setText(arrayList.get(position).getAreaName());
                    break;
                case ITEM_CONTENT:
                    ((ContentViewHolder) holder).contentTextView.setText(arrayList.get(position).getAreaName());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class HeadViewHolder extends BaseViewHolder {
            public TextView headTextView;

            public HeadViewHolder(View itemView) {
                super(itemView);
                headTextView = (TextView) itemView.findViewById(R.id.headTextView);
            }
        }

        class ContentViewHolder extends BaseViewHolder {
            public TextView contentTextView;

            public ContentViewHolder(View itemView) {
                super(itemView);
                contentTextView = (TextView) itemView.findViewById(R.id.contentTextView);
            }
        }

        class BaseViewHolder extends RecyclerView.ViewHolder {

            public BaseViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private void makeData() {
        String fromAssets = AssetsUtils.getFromAssets(StickyHeadActivity.this,"city2.json");// file:///android_asset/city2.json
        Gson gson = new Gson();
        cityBean = gson.fromJson(fromAssets, CityBean.class);
    }


}
