package com.junwu.mvpattempt.ui.fragments.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.fragments.BaseFragment;
import com.junwu.mvpattempt.ui.activitys.home.HomeActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/15 14:40
 * ===============================
 */
public class ViewPagerFragement extends BaseFragment {

    @BindView(R.id.tv_text)
    TextView tvText;

    private String message;

    public static ViewPagerFragement instance(String message) {
        ViewPagerFragement fragement = new ViewPagerFragement();
        fragement.message = message;
        return fragement;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragement_viewpager;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", message);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            savedInstanceState.putString("message", message);
    }

    @Override
    public void initData() {
        tvText.setText(String.format(Locale.CANADA, "当前是第：%s页", message));
    }

    @OnClick(R.id.tv_text)
    void onClickListener(View view) {
        startActivity(new Intent(mContext, HomeActivity.class));
    }


}
