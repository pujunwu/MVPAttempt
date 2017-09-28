package com.junwu.mvpattempt.ui.fragments.home;

import com.junwu.mvpattempt.entitys.DataEntity;
import com.junwu.mvplibrary.mvp.view.IView;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/26 10:24
 * ===============================
 */
public class Contract {

    interface View extends IView {
        void itemOnClickListener(DataEntity entity);
    }

}
