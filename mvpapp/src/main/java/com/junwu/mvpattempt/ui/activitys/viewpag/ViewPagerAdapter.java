package com.junwu.mvpattempt.ui.activitys.viewpag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.LruCache;

import com.junwu.mvpattempt.ui.fragments.viewpager.ViewPagerFragement;

/**
 * ===============================
 * 描    述：
 * 作    者：pjw
 * 创建日期：2017/9/15 14:39
 * ===============================
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int count;

    public ViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerFragement.instance(position + "");
    }

    @Override
    public int getCount() {
        return count;
    }

}
