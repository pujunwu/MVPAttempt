package com.junwu.mvpattempt.ui.fragments.start;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.fragments.BaseFragment;
import com.junwu.mvpattempt.base.fragments.LazyBaseFragment;
import com.junwu.mvpattempt.ui.activitys.home.HomeActivity;
import com.junwu.mvpattempt.ui.activitys.viewpag.ViewPagerActivity;
import com.junwu.mvplibrary.widget.commonadapter.RecyclerAdapter;
import com.junwu.mvplibrary.widget.commonadapter.viewholders.RecyclerViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/15 10:38
 * ===============================
 */
public class StartFragment extends LazyBaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_text)
    RecyclerView rvText;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_start;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initData() {
        toolbar.setTitle("123");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        final ArrayList<DataEntity> datas = getData();

        RecyclerAdapter<DataEntity> recyclerAdapter = new RecyclerAdapter<DataEntity>(R.layout.item_start_rv) {
            @Override
            protected void onBindData(RecyclerViewHolder viewHolder, int position, DataEntity item) {
                viewHolder.setText(R.id.tv_message, item.title);
            }
        };
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (datas.get(position).mClass != null) {
                    startActivity(new Intent(mContext, datas.get(position).mClass));
                }
            }
        });
        recyclerAdapter.addItems(datas);
        rvText.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvText.setAdapter(recyclerAdapter);
    }

    private ArrayList<DataEntity> getData() {
        ArrayList<DataEntity> datas = new ArrayList<>(10);
        datas.add(new DataEntity("普通模式", HomeActivity.class));
        datas.add(new DataEntity("ViewPager模式", ViewPagerActivity.class));
        return datas;
    }

    private class DataEntity {
        String title;
        Class<?> mClass;

        private DataEntity(String title, Class<?> mClass) {
            this.title = title;
            this.mClass = mClass;
        }
    }

}
