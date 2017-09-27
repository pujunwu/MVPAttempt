package com.junwu.mvplibrary.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junwu.mvplibrary.delegate.IFragment;
import com.junwu.mvplibrary.mvp.view.IView;

/**
 * ===============================
 * 描    述：所有Fragment继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/14 11:48
 * ===============================
 */
public abstract class LibBaseFragment extends Fragment implements IFragment, IView {

    protected View rootView;
    //当前上下文
    protected Context mContext;

    public LibBaseFragment() {
        setArguments(getBundle());
    }

    /**
     * 获取Bundle
     *
     * @return Bundle
     */
    protected Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInject();//执行注入
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView = initView(inflater, container);
    }

    /**********************{@link IFragment 接口实现}*********************/

    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean isLazyLoad() {
        return false;
    }

    /**********************{@link IView 接口实现}*********************/
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**********************定义的其他方法*********************/

    /**
     * 获取布局的方法
     */
    public View initView(LayoutInflater inflater, ViewGroup container) {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            return inflater.inflate(layoutId, container, false);
        }
        return getLayoutView();
    }

    /**********************其他生命周期方法*********************/

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onAttach(Context context) {//api版本低于23不会调用
        super.onAttach(context);
        onAttachToContext(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /**
     * onAttach替代方法
     *
     * @param context 上下文
     */
    protected void onAttachToContext(Context context) {
        mContext = context;
    }
}
