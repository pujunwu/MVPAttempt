package com.junwu.basicslibrary.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * ===============================
 * 描    述：当前Phone工具类
 * 作    者：pjw
 * 创建日期：2017/7/21 11:32
 * ===============================
 */
public class PhoneUtils {

    private static int screenHeight = 0;
    private static int screenWidth = 0;
    private static long phoneTotalMemory = 0;

    /**
     * sd卡是否插入
     *
     * @return
     */
    public static boolean isSdCardExit() {
        try {
            if (Environment.getExternalStorageState() != null) {
                return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardRootPath() {
        try {
            if (Environment.getExternalStorageDirectory() != null) {
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取app安装根目录
     *
     * @return
     */
    @SuppressLint("SdCardPath")
    public static String getAppRootPath(Context context) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + context.getApplicationContext().getPackageName();
    }

    /**
     * 获取包信息
     *
     * @return 包信息
     */
    public static PackageInfo getVersionInfo(Context context) {
        try {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            PackageInfo pInfo = pm.getPackageInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 当前系统版本大于等于输入的版本号
     *
     * @param versionCode
     * @return
     */
    public static boolean gtVersion(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    /**
     * 获取屏幕信息
     *
     * @param activity
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) activity).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
            screenWidth = size.x;
        }
        return screenHeight;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return 0;
        }
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
            screenWidth = size.x;
        }
        return screenWidth;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getApplicationContext().getResources().getDimensionPixelSize(x);
            return sbar;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }


    /**
     * 根据手机分辨率从dp转成px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null || context.getApplicationContext() == null) {
            return 0;
        }
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 从sp到px
     *
     * @param spValue
     * @return
     */
    public static int px2dip(Context context, float spValue) {
        final float fontScale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (spValue / fontScale + 0.5f);
    }

    /**
     * 从sp到px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 从px到sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 显示软键盘
     *
     * @param activity 当前处于活动的activity
     */
    public static InputMethodManager displayKeyboard(Activity activity) {
        return displayKeyboard(activity, null);
    }

    /**
     * 显示输入法键盘
     *
     * @param activity 当前处于活动的activity
     */
    public static InputMethodManager displayKeyboard(Activity activity, InputMethodManager imm) {
        if (imm == null) {
            imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        }
        if (!imm.isActive(activity.getWindow().getDecorView())) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            imm.showSoftInput(activity.getWindow().getDecorView().getRootView(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
        return imm;
    }

    /**
     * 关闭软键盘
     *
     * @param activity 当前处于活动的activity
     */
    public static InputMethodManager hideKeyboard(Activity activity) {
        return hideKeyboard(activity, null);
    }

    /**
     * 关闭软键盘
     *
     * @param activity 当前处于活动的activity
     */
    public static InputMethodManager hideKeyboard(Activity activity, InputMethodManager imm) {
        if (imm == null) {
            imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return imm;
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return 不包括虚拟功能高度
     */
    public static int getNoHasVirtualKey(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * 隐藏虚拟按键
     *
     * @param activity Activity
     */
    public static void hideVirtualKey(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);//API19
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 获取本机手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = telephonyManager.getLine1Number();
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }
        if (phoneNumber.startsWith("+86")) {
            if (phoneNumber.length() >= 11) {
                return phoneNumber.substring(4);
            }
            return "";
        }
        if (phoneNumber.matches("0{2,}")) {
            return "";
        }
        return phoneNumber;
    }


    /**
     * 沉浸式状态栏适配
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void initSystemBar(Activity activity) {
        if (PhoneUtils.hasLollipop()) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (PhoneUtils.hasKitKat()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 是否大于等于4.4
     *
     * @return 是返回true，反之返回false
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 是否大于等于5.0
     *
     * @return 是返回true，反之返回false
     */
    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }


    /**
     * 获取当前手机总内存大小
     *
     * @return 内存大小 单位KB
     */
    public static long getTotalMemory() {
        if (phoneTotalMemory > 0) {
            return phoneTotalMemory;
        }
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        BufferedReader localBufferedReader = null;
        try {
            FileReader localFileReader = new FileReader(str1);
            localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        phoneTotalMemory = initial_memory;
        return initial_memory;// KB
    }

    /**
     * 调用系统分享
     *
     * @param context 上下文
     * @param title   分享的标题
     * @param message 分享内容
     */
    public static void systemShare(Context context, String title, String message) {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(textIntent, title));
    }

}
