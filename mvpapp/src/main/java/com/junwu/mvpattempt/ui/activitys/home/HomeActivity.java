package com.junwu.mvpattempt.ui.activitys.home;

import android.support.v4.app.FragmentTransaction;

import com.junwu.basicslibrary.utils.HandlerUtil;
import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.activitys.BaseActivity;
import com.junwu.mvpattempt.ui.fragments.home.HomeFragment;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/14 17:39
 * ===============================
 */
public class HomeActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initData() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new HomeFragment());
        ft.commit();
    }

    @Override
    public void initInject() {

    }

    @Override
    public boolean fragment() {
        return true;
    }

}
