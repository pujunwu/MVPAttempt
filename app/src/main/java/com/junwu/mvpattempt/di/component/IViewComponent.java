package com.junwu.mvpattempt.di.component;

import com.junwu.mvpattempt.di.module.ModelModule;
import com.junwu.mvpattempt.di.module.UtilsModule;
import com.junwu.mvpattempt.di.module.ViewModule;
import com.junwu.mvpattempt.ui.fragments.home.HomeFragment;
import com.junwu.mvpattempt.ui.fragments.start.StartFragment;
import com.junwu.mvplibrary.di.component.AppComponent;
import com.junwu.mvplibrary.di.scope.ViewScope;

import dagger.Component;

/**
 * ===============================
 * 描    述：注入连接文件
 * 作    者：pjw
 * 创建日期：2017/7/20 11:38
 * ===============================
 */
@ViewScope
@Component(modules = {ViewModule.class, ModelModule.class, UtilsModule.class}, dependencies = AppComponent.class)
public interface IViewComponent {

    /*****************************activity注入***************************/
//    void inject(StartActivity activity);

    /*****************************Fragment注入***************************/
    void inject(StartFragment fragment);

    void inject(HomeFragment fragment);

    /*****************************其他注入***************************/


}
