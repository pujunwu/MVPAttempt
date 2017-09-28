package com.junwu.mvpattempt.base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.junwu.mvpattempt.app.App;
import com.junwu.mvpattempt.di.component.IViewComponent;
import com.junwu.mvpattempt.di.module.ViewModule;
import com.junwu.mvplibrary.ui.activity.LibBaseActivity;

/**
 * ===============================
 * 描    述：app模块下所有Activity继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/14 16:05
 * ===============================
 */
public abstract class BaseActivity extends LibBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);//android的默认背景不为空
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取IViewComponent
     *
     * @return IViewComponent
     */
    protected IViewComponent getIViewComponent() {
        return App.sApp.getIViewComponent(getViewModule());
    }

    protected ViewModule getViewModule() {
        return new ViewModule(this, this);
    }


}
