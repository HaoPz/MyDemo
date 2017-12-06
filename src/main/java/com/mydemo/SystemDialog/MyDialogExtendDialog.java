package com.mydemo.SystemDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mydemo.R;

/**
 * Created by HaoPz on 2017/12/6.
 */

public class MyDialogExtendDialog extends AlertDialog {
    Context context;

    protected MyDialogExtendDialog(@NonNull Context context) {
        this(context, 0);
    }

    protected MyDialogExtendDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_dialog_extend, null);
        setContentView(inflate);

        TextView close = (TextView) inflate.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyDialogClickListener.onMyDialogClickListener();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public OnMyDialogClickListener onMyDialogClickListener;

    public void setOnMyDialogClickListener(OnMyDialogClickListener onMyDialogClickListener) {
        this.onMyDialogClickListener = onMyDialogClickListener;
    }

    /**
     * inflate 布局对外提供的接口
     */
    interface OnMyDialogClickListener {
        void onMyDialogClickListener();
    }
}
