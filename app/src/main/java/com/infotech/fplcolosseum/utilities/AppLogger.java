package com.infotech.fplcolosseum.utilities;

import android.util.Log;

import com.blankj.utilcode.BuildConfig;

public class AppLogger {
    private static final boolean LOG_ENABLED = BuildConfig.DEBUG; // Or set to false for release

    public static void d(String tag, String msg) {
        if (LOG_ENABLED) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLED) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_ENABLED) {
            Log.i(tag, msg);
        }
    }

    // Add other Log methods (v, w, wtf) as needed
}
