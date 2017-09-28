package com.junwu.basicslibrary.delegate.impls;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.junwu.basicslibrary.delegate.FragmentDelegate;
import com.junwu.basicslibrary.delegate.IFragment;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ===============================
 * 描    述：fragment生命周期事件代理类
 * 作    者：pjw
 * 创建日期：2017/6/29 10:34
 * ===============================
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    private android.support.v4.app.Fragment mFragment;
    private android.app.Fragment mFragment1;
    //fragment实现类
    private IFragment iFragment;
    private Unbinder mUnbinder;

    public FragmentDelegateImpl(android.support.v4.app.Fragment fragment) {
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    public FragmentDelegateImpl(android.app.Fragment fragment) {
        this.mFragment1 = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (iFragment.isLazyLoad()) {
            return;
        }
        onCreate();
    }

    @Override
    public void onCreate() {
        if (!iFragment.useEventBus()) {
            return;
        }
        if (mFragment != null) {
            EventBus.getDefault().register(mFragment);
        } else if (mFragment1 != null) {
            EventBus.getDefault().register(mFragment1);
        }
    }

    @Override
    public void onCreateView(View view, Bundle savedInstanceState) {
        if (iFragment.isLazyLoad()) {
            return;
        }
        onCreateView(view);
    }

    @Override
    public void onCreateView(View view) {
        if (view == null) {
            return;
        }
        if (mFragment != null) {
            mUnbinder = ButterKnife.bind(mFragment, view);
        } else if (mFragment1 != null) {
            mUnbinder = ButterKnife.bind(mFragment1, view);
        }
        iFragment.initData();//初始化操作
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {

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
    public void onDestroyView() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        if (!iFragment.isLazyLoad() && iFragment.useEventBus()) {
            if (mFragment != null)
                EventBus.getDefault().unregister(mFragment);
            else if (mFragment1 != null)
                EventBus.getDefault().unregister(mFragment1);
        }
        mFragment = null;
        mFragment1 = null;
        iFragment = null;
        mUnbinder = null;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public boolean isCreate() {
        if ((mFragment == null && mFragment1 == null) || iFragment == null || mUnbinder == Unbinder.EMPTY) {
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.readParcelable(android.support.v4.app.Fragment.class.getClassLoader());
        dest.readParcelable(android.app.Fragment.class.getClassLoader());
        dest.readParcelable(IFragment.class.getClassLoader());
        dest.readParcelable(Unbinder.class.getClassLoader());
    }

    protected FragmentDelegateImpl(Parcel in) {
        this.mFragment = in.readParcelable(android.support.v4.app.Fragment.class.getClassLoader());
        this.mFragment1 = in.readParcelable(android.app.Fragment.class.getClassLoader());
        this.iFragment = in.readParcelable(IFragment.class.getClassLoader());
        this.mUnbinder = in.readParcelable(Unbinder.class.getClassLoader());
    }

    public static final Parcelable.Creator<FragmentDelegateImpl> CREATOR = new Parcelable.Creator<FragmentDelegateImpl>() {
        @Override
        public FragmentDelegateImpl createFromParcel(Parcel source) {
            return new FragmentDelegateImpl(source);
        }

        @Override
        public FragmentDelegateImpl[] newArray(int size) {
            return new FragmentDelegateImpl[size];
        }
    };

}
