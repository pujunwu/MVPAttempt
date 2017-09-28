package com.junwu.basicslibrary.sharedpre;

import android.content.Context;

import java.util.Set;

/**
 * ===============================
 * 描    述：本地缓存SecuritySharedPre的基类
 * 作    者：pjw
 * 创建日期：2017/8/1 11:47
 * ===============================
 */
public class BaseSharedPre {

    private SecuritySharedPre mPreferences;
    private SecuritySharedPre.SecurityEditor mEditor;

    /**
     * 可以选择是否加密的SharedPreferences，默认采用加密
     *
     * @param context 建议出入ApplicationContext
     * @param name    存储的文件名称
     */
    public BaseSharedPre(Context context, String name) {
        this(context, name, Context.MODE_PRIVATE, true);
    }

    /**
     * 可以选择是否加密的SharedPreferences，默认访问权限为Context.MODE_PRIVATE
     *
     * @param context      建议出入ApplicationContext
     * @param name         存储的文件名称
     * @param isEncryption 是否加密
     */
    public BaseSharedPre(Context context, String name, boolean isEncryption) {
        this(context, name, Context.MODE_PRIVATE, isEncryption);
    }

    /**
     * 可以选择是否加密的SharedPreferences
     *
     * @param context      建议出入ApplicationContext
     * @param name         存储的文件名称
     * @param mode         访问权限
     * @param isEncryption 是否加密
     */
    public BaseSharedPre(Context context, String name, int mode, boolean isEncryption) {
        mPreferences = new SecuritySharedPre(context, name, mode, isEncryption);
        mEditor = mPreferences.edit();
    }

    /***********************************保存*********************************/
    public void put(String key, String value) {
        mEditor.putString(key, value);
        apply();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        apply();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        apply();
    }

    public void put(String key, float value) {
        mEditor.putFloat(key, value);
        apply();
    }

    public void put(String key, Set<String> values) {
        mEditor.putStringSet(key, values);
        apply();
    }

    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        apply();
    }

    /**********************************获取值********************************/
    public String getValue(String key, String value) {
        return mPreferences.getString(key, value);
    }

    public int getValue(String key, int value) {
        return mPreferences.getInt(key, value);
    }

    public long getValue(String key, long value) {
        return mPreferences.getLong(key, value);
    }

    public float getValue(String key, float value) {
        return mPreferences.getFloat(key, value);
    }

    public Set<String> getValue(String key, Set<String> values) {
        return mPreferences.getStringSet(key, values);
    }

    public boolean getValue(String key, boolean value) {
        return mPreferences.getBoolean(key, value);
    }

    /**********************************获取值，有默认值********************************/

    public String getValueString(String key) {
        return mPreferences.getString(key, "");
    }

    public int getValueInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public long getValueLong(String key) {
        return mPreferences.getLong(key, 0);
    }

    public float getValueFloat(String key) {
        return mPreferences.getFloat(key, 0.0f);
    }

    public Set<String> getValueSet(String key) {
        return mPreferences.getStringSet(key, null);
    }

    public boolean getValueBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    /********************************其他操作********************************/

    protected void clear() {
        mEditor.clear();
    }

    protected void remove(String key) {
        mEditor.remove(key);
        apply();
    }

    /**
     * 提交保存
     */
    protected void apply() {
        mEditor.apply();
    }

}
