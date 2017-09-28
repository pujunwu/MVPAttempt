package com.junwu.mvpattempt.ui.fragments.start;

import com.junwu.basicslibrary.widget.commonadapter.RecyclerAdapter;
import com.junwu.mvpattempt.base.presenter.IBasePresenter;
import com.junwu.mvpattempt.entitys.DataEntity;
import com.junwu.mvplibrary.mvp.model.IModel;
import com.junwu.mvplibrary.mvp.view.IView;

import java.util.ArrayList;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/26 9:52
 * ===============================
 */
public interface StartContract {

    interface View extends IView {
        void itemOnClickListener(DataEntity entity);
    }

    interface Model extends IModel {
        ArrayList<DataEntity> getData();
    }

    interface Presenter extends IBasePresenter {
        RecyclerAdapter getAdapter();
    }

}
