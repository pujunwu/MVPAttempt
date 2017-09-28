package com.junwu.basicslibrary.delegate.impls;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.junwu.basicslibrary.delegate.ActivityDelegate;
import com.junwu.basicslibrary.delegate.IActivity;

/**
 * ===============================
 * 描    述：activity生命周期监听类
 * 作    者：pjw
 * 创建日期：2017/6/27 17:16
 * ===============================
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private FragmentLifecycle mFragmentLifecycle;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        IActivity iActivity = activity instanceof IActivity ? (IActivity) activity : null;
        if (iActivity == null) {
            return;
        }
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate == null && activity.getIntent() != null) {
            delegate = new ActivityDelegateImpl(activity);
            activity.getIntent().putExtra(ActivityDelegate.ACTIVITY_DELEGATE, delegate);
        }
        if (delegate != null) {
            delegate.onCreate(savedInstanceState);
        }
        //添加fragment监听
        if (activity instanceof FragmentActivity && iActivity.fragment()) {
            if (mFragmentLifecycle == null) {
                mFragmentLifecycle = new FragmentLifecycle();
            }
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityDelegate delegate = getActivityDelegate(activity);
        if (delegate != null) {
            delegate.onDestroy();
            activity.getIntent().removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
    }

    private ActivityDelegate getActivityDelegate(Activity activity) {
        if (activity instanceof IActivity && activity.getIntent() != null) {
            return activity.getIntent().getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return null;
    }
}
