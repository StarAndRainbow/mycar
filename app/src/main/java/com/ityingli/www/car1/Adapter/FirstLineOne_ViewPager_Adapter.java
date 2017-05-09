package com.ityingli.www.car1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
public class FirstLineOne_ViewPager_Adapter  extends FragmentPagerAdapter{
    private List<Fragment> datas;
    public FirstLineOne_ViewPager_Adapter(FragmentManager fm,List<Fragment> datas) {
        super(fm);
        this.datas  = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
