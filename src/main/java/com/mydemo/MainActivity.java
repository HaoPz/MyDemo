package com.mydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.CircleImage.CircleImageActivity;
import com.mydemo.DiyProgressBar.DiyProgressBarActivity;
import com.mydemo.FlowViewActivity.FlowViewActivity;
import com.mydemo.StickyHeadActivity.StickyHeadActivity;
import com.mydemo.SwipeMenuView.SwipeMenuViewActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> list = new ArrayList<>();
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mainAdapter = new MainAdapter(list);
        MainAdapter mainAdapter = this.mainAdapter;
        mRecyclerView.setAdapter(mainAdapter);

        mainAdapter.setMainOnClickListener(new MainOnClickListener() {
            @Override
            public void mainOnOnClickListener(int position) {
                switch (position) {
                    case 0:// RecyclerView 粘性头布局
                        Intent goStickyHeadIntent = new Intent(MainActivity.this, StickyHeadActivity.class);
                        startActivity(goStickyHeadIntent);
                    case 1:// FlowView 流布局
                        Intent goFlowViewIntent = new Intent(MainActivity.this, FlowViewActivity.class);
                        startActivity(goFlowViewIntent);
                        break;
                    case 2:// 侧滑删除
                        Intent goSwipeMenuViewIntent = new Intent(MainActivity.this, SwipeMenuViewActivity.class);
                        startActivity(goSwipeMenuViewIntent);
                        break;
                    case 3:// 侧滑删除
                        Intent goProgressBarViewIntent = new Intent(MainActivity.this, DiyProgressBarActivity.class);
                        startActivity(goProgressBarViewIntent);
                        break;
                    case 4:// 圆形View
                        Intent goCircleViewIntent = new Intent(MainActivity.this, CircleImageActivity.class);
                        startActivity(goCircleViewIntent);
                        break;
                }
            }
        });
    }

    private void initData() {
        list.add(getResources().getString(R.string.stickyHeadActivity));
        list.add(getResources().getString(R.string.FlowViewActivity));
        list.add(getResources().getString(R.string.SwipeMenuView));
        list.add(getResources().getString(R.string.diy_progressbar));
        list.add(getResources().getString(R.string.circle_view));
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.BaseViewHolder> {

        private ArrayList<String> list;
        private int item = R.layout.layout_main_item;
        public MainOnClickListener mainOnClickListener;

        public void setMainOnClickListener(MainOnClickListener onClickListener) {
            this.mainOnClickListener = onClickListener;
        }

        public MainAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View content = LayoutInflater.from(MainActivity.this).inflate(item, parent, false);
            BaseViewHolder viewHolder = new ContentViewHolder(content);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, final int position) {
            ((ContentViewHolder) holder).mContent.setText(list.get(position));
            ((ContentViewHolder) holder).mContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainOnClickListener.mainOnOnClickListener(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ContentViewHolder extends BaseViewHolder {

            public TextView mContent;

            public ContentViewHolder(View itemView) {
                super(itemView);
                mContent = (TextView) itemView.findViewById(R.id.content);
            }
        }

        public class BaseViewHolder extends RecyclerView.ViewHolder {
            public BaseViewHolder(View itemView) {
                super(itemView);
            }
        }


    }

    public interface MainOnClickListener {
        void mainOnOnClickListener(int position);
    }
}
