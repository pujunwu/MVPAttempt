package com.junwu.mvpattempt.app;

import com.junwu.mvplibrary.config.IRegisterApiModule;
import com.junwu.mvplibrary.http.IRepositoryManager;

/**
 * ===============================
 * 描    述：用于注册网络访问接口和缓存获取接口，建议每个开发者或者模块一一对应一个IRegisterApiModule接口实现
 * 作    者：pjw
 * 创建日期：2017/7/4 15:22
 * ===============================
 */
public class RegisterApiModule implements IRegisterApiModule {

    @Override
    public void registerComponents(IRepositoryManager repositoryManager) {
//        repositoryManager.injectRetrofitService(HomeService.class);
//        repositoryManager.injectCacheService(HomeCache.class);
    }

}
