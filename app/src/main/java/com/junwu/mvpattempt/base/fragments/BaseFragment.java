package com.junwu.mvpattempt.base.fragments;

import android.app.Activity;

import com.junwu.mvpattempt.di.component.DaggerIViewComponent;
import com.junwu.mvpattempt.di.component.IViewComponent;
import com.junwu.mvpattempt.di.module.ModelModule;
import com.junwu.mvpattempt.di.module.UtilsModule;
import com.junwu.mvpattempt.di.module.ViewModule;
import com.junwu.mvplibrary.ui.fragment.LibBaseFragment;

/**
 * ===============================
 * 描    述：app模块下所有Fragment都将继承的基类
 * 如果要实现懒加载，需要拷贝{@link com.junwu.mvplibrary.ui.fragment.LibLazyFragment}类自行修改
 * 建议将{@link com.junwu.mvplibrary.ui.fragment.LibLazyFragment}类的父类改为{@link BaseFragment}即可
 * 作    者：pjw
 * 创建日期：2017/9/14 16:17
 * ===============================
 */
public abstract class BaseFragment extends LibBaseFragment {

    /**
     * 获取IViewComponent
     *
     * @return IViewComponent
     */
    protected IViewComponent getIViewComponent() {
        return DaggerIViewComponent.builder()
                .appComponent(getAppComponent())
                .viewModule(getViewModule())
                .modelModule(new ModelModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    protected ViewModule getViewModule() {
        return new ViewModule((Activity) mContext, this);
    }

}
