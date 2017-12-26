package com.junwu.mvpattempt.ui.fragments.start;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.fragments.MVPBaseFragment;
import com.junwu.mvpattempt.entitys.DataEntity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ===============================
 * 描    述：懒加载测试
 * 作    者：pjw
 * 创建日期：2017/9/15 10:38
 * ===============================
 */
public class StartFragment extends MVPBaseFragment<StartPresenter> implements StartContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rtv_text)
    TextView rtv_text;
    @BindView(R.id.rv_text)
    RecyclerView rvText;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_start;
    }

    @Override
    public void initInject() {
        getIViewComponent().inject(this);
    }

    @Override
    public void initData() {
        toolbar.setTitle("123");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtv_text.setEnabled(true);
                rtv_text.setClickable(true);
            }
        });
        rvText.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvText.setAdapter(mPresenter.getAdapter());
    }

    @Override
    public void itemOnClickListener(DataEntity entity) {
        if (entity.mClass != null) {
            startActivity(new Intent(mContext, entity.mClass));
        }
    }

    @OnClick(R.id.rtv_text)
    void onClickListener() {
        rtv_text.setEnabled(false);
        rtv_text.setClickable(false);
    }

}
