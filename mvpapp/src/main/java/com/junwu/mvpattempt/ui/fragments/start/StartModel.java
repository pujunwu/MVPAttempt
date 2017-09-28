package com.junwu.mvpattempt.ui.fragments.start;

import com.junwu.mvpattempt.base.models.BaseModel;
import com.junwu.mvpattempt.entitys.DataEntity;
import com.junwu.mvpattempt.ui.activitys.home.HomeActivity;
import com.junwu.mvpattempt.ui.activitys.viewpag.ViewPagerActivity;
import com.junwu.mvplibrary.di.scope.ViewScope;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * ===============================
 * 描    述：不需要调用Retrofit和RxCache的任何一个
 * 作    者：pjw
 * 创建日期：2017/9/26 10:00
 * ===============================
 */
@ViewScope
public class StartModel extends BaseModel implements StartContract.Model {

    @Inject
    public StartModel() {
        super();
    }

    @Override
    public ArrayList<DataEntity> getData() {
        ArrayList<DataEntity> datas = new ArrayList<>(10);
        datas.add(new DataEntity("普通模式", HomeActivity.class));
        datas.add(new DataEntity("ViewPager模式", ViewPagerActivity.class));
        return datas;
    }


}
