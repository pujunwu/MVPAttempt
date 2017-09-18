package com.junwu.mvplibrary.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/6/27 17:35
 * ===============================
 */
public interface ActivityDelegate extends Parcelable {

    String LAYOUT_LINEARLAYOUT = "LinearLayout";
    String LAYOUT_FRAMELAYOUT = "FrameLayout";
    String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

}
