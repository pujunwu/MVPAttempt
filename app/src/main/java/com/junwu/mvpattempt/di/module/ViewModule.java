package com.junwu.mvpattempt.di.module;

import android.app.Activity;

import com.junwu.mvplibrary.di.scope.ViewScope;
import com.junwu.mvplibrary.mvp.view.IView;

import dagger.Module;
import dagger.Provides;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/7/20 11:39
 * ===============================
 */
@Module
public class ViewModule {

    private IView mIView;
    private Activity mActivity;

    public ViewModule(IView view) {
        this(null, view);
    }

    public ViewModule(Activity activity, IView view) {
        mActivity = activity;
        this.mIView = view;
    }

    @ViewScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @ViewScope
    @Provides
    public IView provideIView() {
        return mIView;
    }


}
