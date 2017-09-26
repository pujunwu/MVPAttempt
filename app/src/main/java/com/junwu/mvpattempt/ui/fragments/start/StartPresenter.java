package com.junwu.mvpattempt.ui.fragments.start;

import android.app.Activity;

import com.junwu.mvpattempt.R;
import com.junwu.mvpattempt.base.presenter.BasePresenter;
import com.junwu.mvpattempt.entitys.DataEntity;
import com.junwu.mvplibrary.di.scope.ViewScope;
import com.junwu.mvplibrary.mvp.view.IView;
import com.junwu.mvplibrary.widget.commonadapter.RecyclerAdapter;
import com.junwu.mvplibrary.widget.commonadapter.viewholders.RecyclerViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * ===============================
 * 描    述：Presenter
 * 作    者：pjw
 * 创建日期：2017/9/26 9:51
 * ===============================
 */
@ViewScope
public class StartPresenter extends BasePresenter<StartContract.Model, StartContract.View> implements StartContract.Presenter {

    private RecyclerAdapter<DataEntity> mRecyclerAdapter;
    private ArrayList<DataEntity> mDataEntities;

    @Inject
    public StartPresenter(Activity activity, StartContract.Model m, IView v) {
        super(activity, m, v);
    }

    @Override
    public RecyclerAdapter getAdapter() {
        mDataEntities = mModel.getData();
        if (mRecyclerAdapter != null) {
            mRecyclerAdapter.clear();
            mRecyclerAdapter.addItems(mDataEntities);
            return mRecyclerAdapter;
        }
        mRecyclerAdapter = new RecyclerAdapter<DataEntity>(R.layout.item_start_rv,mDataEntities) {
            @Override
            protected void onBindData(RecyclerViewHolder viewHolder, int position, DataEntity item) {
                viewHolder.setText(R.id.tv_message, item.title);
            }
        };
        mRecyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mView.itemOnClickListener(mDataEntities.get(position));
            }
        });
        return mRecyclerAdapter;
    }


}
