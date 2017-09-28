package com.junwu.ktmvplibrary.ui.fragment

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.junwu.basicslibrary.delegate.IFragment
import com.junwu.ktmvplibrary.mvp.view.IView

/**
 * ===============================
 * 描    述：所有Fragment继承的基类
 * 作    者：pjw
 * 创建日期：2017/9/28 11:25
 * ===============================
 */
abstract class LibBaseFragment : Fragment(), IFragment, IView {

    open protected var rootView: View? = null
    //当前上下文
    protected var mContext: Context? = null

    init {
        arguments = getBundle()
    }

    /**
     * 获取Bundle
     *
     * @return Bundle
     */
    protected fun getBundle(): Bundle {
        var bundle: Bundle? = arguments
        if (bundle == null) {
            bundle = Bundle()
        }
        return bundle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()//执行注入
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = initView(inflater, container)
        return rootView
    }

    /**********************[接口实现][IFragment] */

    override fun getLayoutView(): View? {
        return null
    }

    override fun useEventBus(): Boolean {
        return false
    }

    override fun isLazyLoad(): Boolean {
        return false
    }

    /**********************[接口实现][IView] */
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

    /**********************定义的其他方法*********************/

    /**
     * 获取布局的方法
     */
    open fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        val layoutId = layoutId
        return if (layoutId != 0) {
            inflater!!.inflate(layoutId, container, false)
        } else layoutView
    }

    /**********************其他生命周期方法 */

    @TargetApi(Build.VERSION_CODES.M)
    override fun onAttach(context: Context?) {//api版本低于23不会调用
        super.onAttach(context)
        onAttachToContext(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity)
        }
    }

    /**
     * onAttach替代方法
     *
     * @param context 上下文
     */
    protected fun onAttachToContext(context: Context?) {
        mContext = context
    }

}