package com.junwu.mvplibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.junwu.mvplibrary.delegate.AppDelegate;
import com.junwu.mvplibrary.delegate.IActivity;
import com.junwu.mvplibrary.di.component.AppComponent;
import com.junwu.mvplibrary.mvp.view.IView;

/**
 * ===============================
 * 描    述：所有activity继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/14 11:38
 * ===============================
 */
public abstract class LibBaseActivity extends AppCompatActivity implements IActivity, IView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();//执行注入
        initData();//初始化
    }

    /************************{@link IActivity 接口实现}************************/

    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public boolean eventBus() {
        return false;
    }

    @Override
    public boolean fragment() {
        return false;
    }

    /************************{@link IView 接口实现}************************/

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /************************其他方法************************/

}
