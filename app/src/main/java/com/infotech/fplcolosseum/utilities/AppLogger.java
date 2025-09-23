package com.infotech.fplcolosseum.utilities;
import com.orhanobut.logger.Logger;
import com.infotech.fplcolosseum.BuildConfig;  // ✅ Correct BuildConfig

public class AppLogger {
    private static final boolean LOG_ENABLED = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (LOG_ENABLED) {
            Logger.d(msg);
        }
    }

    public static void e(String msg) {
        if (LOG_ENABLED) {
            Logger.e(msg);
        }
    }

    public static void i(String msg) {
        if (LOG_ENABLED) {
            Logger.i(msg);
        }
    }
}
