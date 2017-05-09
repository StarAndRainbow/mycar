package com.ityingli.www.car1.Pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ityingli.www.car1.Activitypake.FirstLine_One_CarConditionActivity;
import com.ityingli.www.car1.Activitypake.MyApplication;
import com.ityingli.www.car1.R;
import com.ityingli.www.car1.View.DashboardView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/14.
 */
public class HomePager extends Fragment implements View.OnClickListener {
    List<Map<String,Object>>  Datas = null;
    DashboardView dashboardView;
    private LinearLayout first_line_one;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  Toast.makeText(getContext(),"加载首页",Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.layout_homepager,container,false);
        initView(view);
        initEvent();


      /*
      * 仪表盘的动作的线程s
      * */
       Thread t1 =  new Thread(){
            public void run(){
               // Toast.makeText(getActivity(),"创建线程",Toast.LENGTH_SHORT).show();//报错
                //System.out.println("创建线程");

                for(int i = 0 ;i<=200;i++){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    dashboardView.setUpdataNumber(i);
                    if(i%2==0)
                    dashboardView.updataTextNumber(i/2);
                    dashboardView.postInvalidate();
                }
            }
        };


     /*
     * 判断是否启动仪表盘的动画
     * */
     if(!MyApplication.gett1IsStart()){
         t1.start();
         MyApplication.sett1IsStart(true);
     }
        return view;
    }



     /*
     * 初始化事件
     * */
    private void initEvent() {
        first_line_one.setOnClickListener(this);
    }


    /*
    * 初始化控件
    * */
    private void initView(View view) {
        dashboardView = (DashboardView) view.findViewById(R.id.dashboardView_id);
        first_line_one = (LinearLayout) view.findViewById(R.id.first_line_one);
    }

    /*
    * 控件的点击事件动作
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.first_line_one:
                Intent intent = new Intent(getContext(), FirstLine_One_CarConditionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
