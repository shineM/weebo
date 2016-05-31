package com.danlvse.weebo.ui.adapter;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by zxy on 16/5/27.
 */
public class CommentPagerAdapter extends FragmentStatePagerAdapter {
    List<android.support.v4.app.Fragment> fragments;
    public CommentPagerAdapter(FragmentManager fm, List<android.support.v4.app.Fragment> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "评论";
            case 1:
                return "转发";
            case 2:
                return "赞";
            default:
                return null;
        }
    }
}
