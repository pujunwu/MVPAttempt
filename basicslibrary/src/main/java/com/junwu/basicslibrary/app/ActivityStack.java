package com.junwu.basicslibrary.app;

import android.app.ActivityManager;
import android.content.Context;

/**
 * ===============================
 * 描    述：Activity任务栈
 * 作    者：pjw
 * 创建日期：2017/8/22 15:31
 * ===============================
 */
public class ActivityStack {

    /**
     * 静态类部类
     */
    private static class ActivityStackInstance {
        private final static ActivityStack INSTANCE = new ActivityStack();
    }

    /**
     * 静态内部类单例
     */
    public static ActivityStack getInstance() {
        return ActivityStackInstance.INSTANCE;
    }

    private ActivityStack() {
    }

    /**
     * 退出程序
     *
     * @param context 上下文
     */
    public void exit(Context context) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
