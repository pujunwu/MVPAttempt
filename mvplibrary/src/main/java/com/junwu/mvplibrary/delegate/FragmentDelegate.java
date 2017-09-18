package com.junwu.mvplibrary.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

/**
 * ===============================
 * 描    述：FragmentDelegate
 * 作    者：pjw
 * 创建日期：2017/6/29 10:33
 * ===============================
 */
public interface FragmentDelegate extends Parcelable {

    String FRAGMENT_DELEGATE = "fragment_delegate";

    void onAttach(Context context);

    void onCreate(Bundle savedInstanceState);

    /**
     * 执行操作的
     */
    void onCreate();

    void onCreateView(View view, Bundle savedInstanceState);

    /**
     * 执行操作的
     */
    void onCreateView(View view);

    void onActivityCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    void onDetach();

    /**
     * 是否创建
     *
     * @return boolean
     */
    boolean isCreate();

}
