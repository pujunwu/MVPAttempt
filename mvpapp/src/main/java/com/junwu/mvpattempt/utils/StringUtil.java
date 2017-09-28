package com.junwu.mvpattempt.utils;

import java.util.ArrayList;

/**
 * ===============================
 * 描    述：字符串处理工具类
 * 作    者：pjw
 * 创建日期：2017/8/2 15:59
 * ===============================
 */
public class StringUtil {

    /**
     * 生成测试数据
     *
     * @param count 条数
     * @return 测试数据
     */
    public static ArrayList<String> getStrings(int count) {
        ArrayList<String> arrayList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            arrayList.add("i:" + i);
        }
        return arrayList;
    }


}
