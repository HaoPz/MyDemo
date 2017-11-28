package com.mydemo.Utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by HaoPz on 2017/11/28.
 */

public class ActivityCollector {
    private static ArrayList<Activity> activityArrayList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityArrayList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityArrayList.remove(activity);
    }

    public static void removeAll() {
        for (Activity activity : activityArrayList) {
            if (activity.isFinishing()) {
                activity.finish();
                activityArrayList.remove(activity);
            }
        }
    }


}
