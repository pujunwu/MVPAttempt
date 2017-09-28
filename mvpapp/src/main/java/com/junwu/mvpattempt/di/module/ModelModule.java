package com.junwu.mvpattempt.di.module;

import com.junwu.mvpattempt.ui.fragments.start.StartContract;
import com.junwu.mvpattempt.ui.fragments.start.StartModel;
import com.junwu.mvplibrary.di.scope.ViewScope;

import dagger.Module;
import dagger.Provides;

/**
 * ===============================
 * 描    述：提供Model的Module
 * 作    者：pjw
 * 创建日期：2017/7/20 15:39
 * ===============================
 */
@Module
public class ModelModule {

    @ViewScope
    @Provides
    StartContract.Model provideStartContractModel(StartModel startModel) {
        return startModel;
    }

}
