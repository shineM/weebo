package com.danlvse.weebo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by zxy on 16/6/14.
 */
public class MessagePagerAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> fragments;
    public MessagePagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "评论";
            case 1:
                return "@我的";
            default:
                return null;
        }
    }
}
