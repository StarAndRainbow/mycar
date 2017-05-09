package com.ityingli.www.car1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class MyPageAdapter extends FragmentPagerAdapter {

    List<Fragment> pagerDatas;
    public MyPageAdapter(FragmentManager fm,List<Fragment> pagerDatas) {
        super(fm);
        this.pagerDatas = pagerDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerDatas.get(position);
    }

    @Override
    public int getCount() {
        return pagerDatas.size();
    }
}
