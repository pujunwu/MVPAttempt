package com.junwu.mvpattempt.ui.activitys.viewpag;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.activitys.BaseActivity;

import butterknife.BindView;

/**
 * ===============================
 * 描    述：ViewPagerActivity
 * 作    者：pjw
 * 创建日期：2017/9/15 11:37
 * ===============================
 */
public class ViewPagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_text)
    ViewPager vpText;

    @Override
    public boolean fragment() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wiewpager;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initData() {
        toolbar.setTitle("ViewPager页面");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vpText.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), 10));
    }

}
