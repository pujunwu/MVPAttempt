package com.junwu.mvpattempt.base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.junwu.mvpattempt.base.presenter.IBasePresenter;

/**
 * ===============================
 * 描    述：所有MVP的activity都将继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/14 16:18
 * ===============================
 */
public abstract class MVPBaseActivity<P extends IBasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

}
