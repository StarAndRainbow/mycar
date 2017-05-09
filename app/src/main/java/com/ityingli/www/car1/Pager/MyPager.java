package com.ityingli.www.car1.Pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ityingli.www.car1.Activitypake.MyPagerSystemSettingActivity;
import com.ityingli.www.car1.R;

/**
 * Created by Administrator on 2017/4/14.
 */
public class MyPager extends Fragment implements View.OnClickListener {
     private LinearLayout btn_system_setting;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_mypager,container,false);
        btn_system_setting = (LinearLayout) view.findViewById(R.id.btn_system_setting);
        btn_system_setting.setOnClickListener(this);
        return view;
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_system_setting:
                Intent intent = new Intent(getActivity(), MyPagerSystemSettingActivity.class);
                startActivity(intent);
                break;
        }
    }



}
