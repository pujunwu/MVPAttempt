package com.junwu.mvpattempt.ui.activitys.start;

import android.support.v4.app.FragmentTransaction;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.activitys.BaseActivity;
import com.junwu.mvpattempt.ui.fragments.start.StartFragment;

/**
 * ===============================
 * 描    述：开始页面
 * 作    者：pjw
 * 创建日期：2017/9/14 17:54
 * ===============================
 */
public class StartActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initInject() {

    }

    @Override
    public boolean fragment() {
        return true;
    }

    @Override
    public void initData() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new StartFragment());
        ft.commit();
    }


}
