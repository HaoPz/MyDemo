package com.mydemo.SystemDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.mydemo.Base.BaseActivity;
import com.mydemo.R;
import com.mydemo.Utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统对话框的学习
 * Created by HaoPz on 2017/12/6.
 */

public class SystemDialogActivity extends BaseActivity {
    @BindView(R.id.puTongDialog)
    TextView puTongDialog;
    @BindView(R.id.listDialog)
    TextView listDialog;
    @BindView(R.id.siginChooseDialog)
    TextView siginChooseDialog;
    @BindView(R.id.muchChooseDialog)
    TextView muchChooseDialog;
    @BindView(R.id.waitDialog)
    TextView waitDialog;
    @BindView(R.id.loadingDialog)
    TextView loadingDialog;
    @BindView(R.id.editDialog)
    TextView editDialog;
    @BindView(R.id.diyDialogExtendDialog)
    TextView diyDialogExtendDialog;
    @BindView(R.id.diyDialog)
    TextView diyDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_dialog_list);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.puTongDialog, R.id.listDialog, R.id.siginChooseDialog,
            R.id.muchChooseDialog, R.id.waitDialog, R.id.loadingDialog,
            R.id.editDialog, R.id.diyDialogExtendDialog, R.id.diyDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.puTongDialog:
                showPuTongDialog();
                break;
            case R.id.listDialog:
                showListDialog();
                break;
            case R.id.siginChooseDialog:
                showSiginChooseDialog();
                break;
            case R.id.muchChooseDialog:
                showMuchChooseDialog();
                break;
            case R.id.waitDialog:
                showWaitDialog();
                break;
            case R.id.loadingDialog:
                showLoadingDialog();
                break;
            case R.id.editDialog:
                showEditDialog();
                break;
            case R.id.diyDialogExtendDialog:
                showDiyDialogExtendDialog();
                break;

            case R.id.diyDialog:
                showDiyDialogInAlertDialog();
                break;
        }
    }

    /**
     * 字面意思：自定义的对话框放到原生的对话框中
     */
    private void showDiyDialogInAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SystemDialogActivity.this);
        builder.setCancelable(false);
        final AlertDialog show = builder.show();
        Window window = show.getWindow();
        View inflate = LayoutInflater.from(SystemDialogActivity.this).inflate(R.layout.my_dialog, null);
        window.setContentView(inflate);

        TextView exit = (TextView) inflate.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.isShowing()) {
                    show.dismiss();
                }
            }
        });
    }

    /**
     * 自定义 ： 实现集成了 Dialog 对话框
     * 自带对话框属性:1.对话框圆角, 2.系统对话框的宽度, 3.显示时候背景为灰色,
     */
    private void showDiyDialogExtendDialog() {
        final MyDialogExtendDialog myDialogExtendDialog = new MyDialogExtendDialog(SystemDialogActivity.this);
        myDialogExtendDialog.show();
        myDialogExtendDialog.setCanceledOnTouchOutside(false);
        myDialogExtendDialog.setOnMyDialogClickListener(new MyDialogExtendDialog.OnMyDialogClickListener() {
            @Override
            public void onMyDialogClickListener() {
                myDialogExtendDialog.dismiss();
            }
        });
    }

    /**
     * 编辑对话框
     */
    private void showEditDialog() {
        /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(SystemDialogActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(SystemDialogActivity.this);
        inputDialog.setTitle("我是一个输入Dialog").setView(editText); // 可以任何一种View
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtils.showShort(SystemDialogActivity.this, editText.getText().toString());
                    }
                }).show();
    }


    /**
     * 进度条对话框
     */
    private void showLoadingDialog() {
        /* @setProgress 设置初始进度
         * @setProgressStyle 设置样式（水平进度条）
         * @setMax 设置进度最大值
                */
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog = new ProgressDialog(SystemDialogActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("我是一个进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
    /* 模拟进度增加的过程
     * 新开一个线程，每个100ms，进度增加1
     */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_PROGRESS) {
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 进度达到最大值后，窗口消失
                progressDialog.cancel();
            }
        }).start();
    }

    /**
     * 等待对话框
     */
    private void showWaitDialog() {
        /* 等待Dialog具有屏蔽其他控件的交互能力
     * @setCancelable 为使屏幕不可点击，设置为不可取消(false)
     * 下载等事件完成后，主动调用函数关闭该Dialog
     */
        ProgressDialog waitingDialog =
                new ProgressDialog(SystemDialogActivity.this);
        waitingDialog.setTitle("我是一个等待Dialog");
        waitingDialog.setMessage("等待中...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(true);
        waitingDialog.show();
    }

    /**
     * 多选对话框
     */
    ArrayList<Integer> yourChoices = new ArrayList<>();

    private void showMuchChooseDialog() {
        final String[] items = {"选项1", "选项2", "选项3", "选项4"};
        // 设置默认选中的选项，全为false默认均未选中
        final boolean initChoiceSets[] = {false, false, false, false};
        yourChoices.clear();
        AlertDialog.Builder multiChoiceDialog =
                new AlertDialog.Builder(SystemDialogActivity.this);
        multiChoiceDialog.setTitle("我是一个多选Dialog");
        multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            initChoiceSets[which] = true;
                        } else {
                            initChoiceSets[which] = false;
                        }
                    }
                });
        multiChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int size = yourChoices.size();
                        String str = "";
                        for (int i = 0; i < size; i++) {
                            str += items[yourChoices.get(i)] + " ";
                        }
                        ToastUtils.showShort(SystemDialogActivity.this, "你选择了" + str);
                    }
                });
        multiChoiceDialog.show();
    }

    /**
     * siginChooseDialog 单选对话框
     */
    int yourChoice;

    private void showSiginChooseDialog() {
        final String[] items = {"选项1", "选项2", "选项3", "选项4"};
        yourChoice = 0;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(SystemDialogActivity.this);
        singleChoiceDialog.setTitle("我是一个单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            ToastUtils.showShort(SystemDialogActivity.this, "你选择了" + items[yourChoice]);
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    /**
     * listDialog 列表对话框
     */
    private void showListDialog() {
        final String[] items = {"列表1", "列表2", "列表3", "列表4"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(SystemDialogActivity.this);
        listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                ToastUtils.showShort(SystemDialogActivity.this, "你点击了" + items[which]);
            }
        });
        listDialog.show();
    }

    /**
     * puTongDialog 普通对话框
     */
    private void showPuTongDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * @setCancelable 内否被点击外部取消
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SystemDialogActivity.this);
        normalDialog.setCancelable(true);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ToastUtils.showShort(SystemDialogActivity.this, "取消");
                    }
                });
        normalDialog.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        ToastUtils.showLong(SystemDialogActivity.this, "确定");
                    }
                });
        // 显示
        normalDialog.show();
    }
}
