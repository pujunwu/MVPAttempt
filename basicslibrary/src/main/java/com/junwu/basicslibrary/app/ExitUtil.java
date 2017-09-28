package com.junwu.basicslibrary.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * ===============================
 * 描    述：双击退出程序
 * 作    者：pjw
 * 创建日期：2017/8/22 15:34
 * ===============================
 */
public class ExitUtil {

    //上下文
    private Activity mActivity;
    //是否执行退出
    private boolean isExit = false;
    //倒计时
    private Handler mHandler = null;
    //倒计时时间
    private long time = 1000;
    //提示
    private String tip = "再点击一次退出程序";
    //倒计时结束运行
    private Runnable mBackRunnable;

    /**
     * 退出程序工具类，两次点击相隔时间默认为500毫秒
     *
     * @param activity 上下文，一般传递首页
     */
    public ExitUtil(Activity activity) {
        this.mActivity = activity;
        mHandler = new Handler(Looper.myLooper());
    }

    /**
     * onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return onBackPressed();
    }

    /**
     * 返回事件
     */
    public boolean onBackPressed() {
        if (isExit) {
            mHandler.removeCallbacks(mBackRunnable);
            mActivity.finish();
            ActivityStack.getInstance().exit(mActivity.getApplicationContext());
        } else {
            isExit = true;
            Toast.makeText(mActivity, tip, Toast.LENGTH_LONG).show();
            mHandler.postDelayed(getBackRunnable(), time);
        }
        return true;
    }

    /**
     * 获取延迟执行内容
     */
    private Runnable getBackRunnable() {
        if (mBackRunnable == null) {
            mBackRunnable = new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            };
        }
        return mBackRunnable;
    }

    /**
     * 两次点击相隔时间默认为1000毫秒
     *
     * @param time 两次点击相隔时间
     */
    public ExitUtil setTime(long time) {
        this.time = time;
        return this;
    }

    /**
     * 第一次触发返回时提示内容
     *
     * @param tip 提示内容
     */
    public ExitUtil setTip(String tip) {
        this.tip = tip;
        return this;
    }
}
