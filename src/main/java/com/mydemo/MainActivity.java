package com.mydemo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydemo.ActivityTransitions.ActivityA;
import com.mydemo.Base.BaseActivity;
import com.mydemo.CarameAlbum.CarameAlbumActivity;
import com.mydemo.CircleImage.CircleImageActivity;
import com.mydemo.DiyProgressBar.DiyProgressBarActivity;
import com.mydemo.FlowViewActivity.FlowViewActivity;
import com.mydemo.OkhttpUtils.TestOkhttp;
import com.mydemo.StatusBarDemo.StatusBarTest;
import com.mydemo.StickyHeadActivity.StickyHeadActivity;
import com.mydemo.StudyRxJava.RxJavaActivity;
import com.mydemo.SwipeMenuView.SwipeMenuViewActivity;
import com.mydemo.SystemDialog.SystemDialogActivity;
import com.mydemo.VerticalScrollText.VerticalScrollTextActivity;

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
                        break;
                    case 1:// FlowView 流布局
                        Intent goFlowViewIntent = new Intent(MainActivity.this, FlowViewActivity.class);
                        startActivity(goFlowViewIntent);
                        break;
                    case 2:// 侧滑删除
                        Intent goSwipeMenuViewIntent = new Intent(MainActivity.this, SwipeMenuViewActivity.class);
                        startActivity(goSwipeMenuViewIntent);
                        break;
                    case 3:// 自定义进度条
                        Intent goProgressBarViewIntent = new Intent(MainActivity.this, DiyProgressBarActivity.class);
                        startActivity(goProgressBarViewIntent);
                        break;
                    case 4:// 圆形View
                        Intent goCircleViewIntent = new Intent(MainActivity.this, CircleImageActivity.class);
                        startActivity(goCircleViewIntent);
                        break;
                    case 5:// RxJava_RxAndroid学习
                        Intent goRxJavaIntent = new Intent(MainActivity.this, RxJavaActivity.class);
                        startActivity(goRxJavaIntent);
                        break;
                    case 6:// okhttp封装使用
                        Intent goOkHttpIntent = new Intent(MainActivity.this, TestOkhttp.class);
                        startActivity(goOkHttpIntent);
                        break;
                    case 7:// 相机和相册调用(兼容到23)
                        Intent goCarameAlbumIntent = new Intent(MainActivity.this, CarameAlbumActivity.class);
                        startActivity(goCarameAlbumIntent);
                        break;
                    case 8:// 各种类型系统对话框学习
                        Intent goSystemDialogIntent = new Intent(MainActivity.this, SystemDialogActivity.class);
                        startActivity(goSystemDialogIntent);
                        break;
                    case 9:// status bar状态栏 学习; 5.0以下 &#45;&#45; 5.0以上区别及实现方式
                        Intent goStatusBarTestIntent = new Intent(MainActivity.this, StatusBarTest.class);
                        startActivity(goStatusBarTestIntent);
                        break;
                    case 10:// Activity 跳转动画 //  makeSceneTransitionAnimation 5.0 以下报错,这里就不处理兼容性
                        // 如果只在 ActivityA 设置 getWindow().setExitTransition(new Fade()); 无效
                        Intent goActivityAIntent = new Intent(MainActivity.this, ActivityA.class);
                        startActivity(goActivityAIntent,
                                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                        break;
                    case 11:// 垂直滚动TextView
                        // 如果只在 ActivityA 设置 getWindow().setExitTransition(new Fade()); 无效
                        Intent goVerticalScrollTextIntent = new Intent(MainActivity.this, VerticalScrollTextActivity.class);
                        startActivity(goVerticalScrollTextIntent,
                                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
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
        list.add(getResources().getString(R.string.rx_study));
        list.add(getResources().getString(R.string.okhttp));
        list.add(getResources().getString(R.string.carame_album));
        list.add(getResources().getString(R.string.system_dialog));
        list.add(getResources().getString(R.string.statusbar));
        list.add(getResources().getString(R.string.activity_transition));
        list.add(getResources().getString(R.string.vertical_scroll_textView));

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
