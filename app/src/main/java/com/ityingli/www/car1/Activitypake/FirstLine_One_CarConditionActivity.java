package com.ityingli.www.car1.Activitypake;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.ityingli.www.car1.Adapter.FirstLineOne_ViewPager_Adapter;
import com.ityingli.www.car1.Pager.MeterShowFragment;
import com.ityingli.www.car1.Pager.NumberShowFragment;
import com.ityingli.www.car1.R;

import java.util.ArrayList;
import java.util.List;

         /*
         *实时车况 now car situation(情况)
         * */
public class FirstLine_One_CarConditionActivity extends FragmentActivity implements View.OnClickListener {


    private ViewPager viewpager;
    private List<Fragment> datas;
    private TabLayout tablayout;
    private ImageButton back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_line__one__car_condition);
        /*
        * 控件初始化
        * */
        initView();
       /*
       * viewPager数据源
       * */
        initDatas();
        /*
        * viewPager的适配器
        * */
        viewpager.setAdapter(new FirstLineOne_ViewPager_Adapter(getSupportFragmentManager(),datas));

        /*
        * tabLayout和Viewpager进行关联
        * */
        tablayout.setupWithViewPager(viewpager);
        /*
        * 初始化TabLayout
         * */
        initTabLayout();
        /*
        * 初始化事件
        * */
        initEvent();
    }

    private void initEvent() {
        back_button.setOnClickListener(this);
    }

    private void initTabLayout() {
        tablayout.getTabAt(0).setText("数字显示");
        tablayout.getTabAt(1).setText("仪表显示");
    }

    private void initDatas() {
        datas = new ArrayList<>();
        datas.add(new NumberShowFragment());
        datas.add(new MeterShowFragment());
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.nowCondition_viewPager_id);
        tablayout = (TabLayout) findViewById(R.id.tabLayout);
        back_button = (ImageButton) findViewById(R.id.back_img_btn_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_img_btn_id:
                finish();
            break;
        }
    }
}
