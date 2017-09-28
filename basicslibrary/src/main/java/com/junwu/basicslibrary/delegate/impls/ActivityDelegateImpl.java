package com.junwu.basicslibrary.delegate.impls;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.junwu.basicslibrary.delegate.ActivityDelegate;
import com.junwu.basicslibrary.delegate.IActivity;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ===============================
 * 描    述：Activity生命周期监听
 * 作    者：pjw
 * 创建日期：2017/6/27 17:35
 * ===============================
 */
public class ActivityDelegateImpl implements ActivityDelegate {

    private Activity mActivity;
    private IActivity iActivity;
    private Unbinder mUnbinder;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int layoutId = iActivity.getLayoutId();
        if (layoutId != 0) {
            mActivity.setContentView(layoutId);
            mUnbinder = ButterKnife.bind(mActivity);
        } else {
            View view = iActivity.getLayoutView();
            if (view != null) {
                mActivity.setContentView(view);
                mUnbinder = ButterKnife.bind(view);
            }
        }
        if (iActivity.eventBus()) {
            EventBus.getDefault().register(mActivity);
        }
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        if (iActivity.eventBus()) {
            EventBus.getDefault().unregister(mActivity);
        }
        mActivity = null;
        iActivity = null;
        mUnbinder = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
        this.mUnbinder = in.readParcelable(Unbinder.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityDelegateImpl> CREATOR = new Parcelable.Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };

}
