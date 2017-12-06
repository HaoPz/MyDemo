package com.mydemo.SystemDialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HaoPz on 2017/12/6.
 */

public class MyDialog extends View {
    public MyDialog(Context context) {
        this(context, null);
    }

    public MyDialog(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
