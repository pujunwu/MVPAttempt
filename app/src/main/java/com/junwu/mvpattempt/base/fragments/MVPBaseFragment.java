package com.junwu.mvpattempt.base.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.junwu.mvpattempt.base.presenter.IBasePresenter;

import javax.inject.Inject;

/**
 * ===============================
 * 描    述：app模块下所有MVP的Fragment基类
 * 如果要实现懒加载，需要拷贝{@link com.junwu.mvplibrary.ui.fragment.LibLazyFragment}类自行修改
 * 建议将{@link com.junwu.mvplibrary.ui.fragment.LibLazyFragment}类的父类改为{@link MVPBaseFragment}即可
 * 作    者：pjw
 * 创建日期：2017/9/14 16:31
 * ===============================
 */
public abstract class MVPBaseFragment<P extends IBasePresenter> extends BaseFragment {

    @Inject
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

}
