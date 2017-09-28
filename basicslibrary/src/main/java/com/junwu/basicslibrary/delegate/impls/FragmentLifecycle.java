package com.junwu.basicslibrary.delegate.impls;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.junwu.basicslibrary.delegate.FragmentDelegate;
import com.junwu.basicslibrary.delegate.IFragment;

/**
 * ===============================
 * 描    述：Fragment生命周期监听，不支持懒加载
 * 作    者：pjw
 * 创建日期：2017/9/14 10:11
 * ===============================
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        Bundle savedInstanceState = getFragementBundle(f, null);
        //如果是IFragment的实现类 并且savedInstanceState不为空 并且不是懒加载就进入
        if (f instanceof IFragment && savedInstanceState != null && !((IFragment) f).isLazyLoad()) {
            FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
            if (fragmentDelegate == null) {
                //创建代理类
                fragmentDelegate = new FragmentDelegateImpl(f);
                savedInstanceState.putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(getFragementBundle(f, savedInstanceState));
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, getFragementBundle(f, savedInstanceState));
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(getFragementBundle(f, savedInstanceState));
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
        FragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
            f.getArguments().clear();
        }
    }

    /**
     * 获取FragmentDelegate
     *
     * @param fragment Fragment
     * @return FragmentDelegate
     */
    private FragmentDelegate getFragmentDelegate(Fragment fragment) {
        if (fragment != null && fragment instanceof IFragment && fragment.getArguments() != null) {
            FragmentDelegate fragmentDelegate = null;
            try {
                fragmentDelegate = fragment.getArguments().getParcelable(FragmentDelegate.FRAGMENT_DELEGATE);
            } catch (Exception e) {
            }
            if (fragmentDelegate == null || !fragmentDelegate.isCreate()) {
                return null;
            }
            return fragmentDelegate;
        }
        return null;
    }

    /**
     * 获取Fragment的Bundle
     *
     * @param f                  Fragment
     * @param savedInstanceState Bundle
     */
    private Bundle getFragementBundle(Fragment f, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return savedInstanceState;
        }
        return f.getArguments();
    }

}