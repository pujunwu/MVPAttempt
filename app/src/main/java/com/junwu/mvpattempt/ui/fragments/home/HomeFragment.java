package com.junwu.mvpattempt.ui.fragments.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.fragments.MVPBaseFragment;
import com.junwu.mvpattempt.ui.activitys.start.StartActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ===============================
 * 描    述：没有Model的Presenter示例
 * 作    者：pjw
 * 创建日期：2017/9/14 17:51
 * ===============================
 */
public class HomeFragment extends MVPBaseFragment<Presenter> {

    @BindView(R.id.tvTest)
    TextView tvTest;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initInject() {
        getIViewComponent().inject(this);
    }

    @Override
    public void initData() {
        tvTest.setText(mPresenter.getDate());
    }

    @OnClick(R.id.tvTest)
    void onClickListener(View view) {
        startActivity(new Intent(mContext, StartActivity.class));
    }

}
