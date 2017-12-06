package com.mydemo.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HaoPz on 2017/12/6.
 */

public class ToastUtils {
    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
