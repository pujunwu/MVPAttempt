package com.junwu.mvplibrary.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/8/28 16:04
 * ===============================
 */
public class HandlerUtil {

    public final static Handler HANDLER = new Handler(Looper.myLooper());

    public static void post(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void postAtTime(Runnable runnable, long uptimeMillis) {
        HANDLER.postAtTime(runnable, uptimeMillis);
    }

    public static void removeCallbacks(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

}
