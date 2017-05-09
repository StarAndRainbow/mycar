package com.ityingli.www.car1.Pager;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ityingli.www.car1.R;
import com.ityingli.www.car1.View.MyLineChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberShowFragment extends Fragment implements View.OnClickListener {

    private View view;
    private FrameLayout card;
    private LinearLayout card_front;
    private LinearLayout card_bg;
    private AnimatorSet left_in;
    private AnimatorSet Right_out;


    private boolean isShow = false;      //是否显示 背面
    private AnimatorSet linechart_left_in;
    private AnimatorSet linechart_right_out;
    private FrameLayout card_lineChart;
    private LinearLayout linechart_textdesc;
    private MyLineChartView linechartView;
    private boolean lineChart_isShow =false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_number_showk, container, false);
        /*
        * 初始化控件
        * */
        initEvent();
        /*
         * 刚进来的时候，让背面隐藏。点击之后就执行动画并且恢复显示
         * */
        card_bg.setVisibility(View.GONE);
        linechartView.setVisibility(View.GONE);
        return view;
    }

    private void initEvent() {
         card = (FrameLayout)view.findViewById(R.id.card);
         card_front = (LinearLayout) view.findViewById(R.id.card_front);
         card_bg = (LinearLayout) view.findViewById(R.id.card_bg);


        card_lineChart = (FrameLayout) view.findViewById(R.id.card_charLinet);
        linechart_textdesc = (LinearLayout) view.findViewById(R.id.linechart_textdesc);
        linechartView = (MyLineChartView) view.findViewById(R.id.linechartView);

        //设置动画
        setAnimation();
        //设置镜头距离
       setCameraDistance();
        //设置点击事件
        card.setOnClickListener(this);
        card_lineChart.setOnClickListener(this);
    }

    private void setCameraDistance() {      //这个方法还没懂，继续修炼吧
        // 改变视角距离, 贴近屏幕 
            int distance = 16000;
            float scale = getResources().getDisplayMetrics().density * distance;
            card_front.setCameraDistance(scale);
            card_bg.setCameraDistance(scale);

        linechart_textdesc.setCameraDistance(scale);
        linechartView.setCameraDistance(scale);
    }


    private void flipCard() {
        if (!isShow) { // 正面朝上
            Right_out.setTarget(card_front);
            left_in.setTarget(card_bg);
            Right_out.start();
            left_in.start();
            isShow = true;
        } else { // 背面朝上
            Right_out.setTarget(card_bg);
            left_in.setTarget(card_front);
            Right_out.start();
            left_in.start();
            isShow = false;
        }
    }




    private void card_linechart(){
        if(!lineChart_isShow){
            linechart_right_out.setTarget(linechart_textdesc);
            linechart_left_in.setTarget(linechartView);
            linechart_right_out.start();
            linechart_left_in.start();
            lineChart_isShow = true;
        }else{
            linechart_right_out.setTarget(linechartView);
            linechart_left_in.setTarget(linechart_textdesc);
            linechart_right_out.start();
            linechart_left_in.start();
            lineChart_isShow = false;}
    }

    //点击事件
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.card:
                card_bg.setVisibility(View.VISIBLE);
                flipCard();
                break;
            case R.id.card_charLinet:
                linechartView.setVisibility(View.VISIBLE);
                card_linechart();
                break;
        }
    }




    private void setAnimation() {
      left_in  = (AnimatorSet)AnimatorInflater.loadAnimator(getContext(),R.animator.anim_left_in);
      Right_out  = (AnimatorSet)AnimatorInflater.loadAnimator(getContext(),R.animator.anim_right_out);

        linechart_left_in = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.anim_linechar_left_in);
        linechart_right_out = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.anim_linechart_right_out);
      /*
      * 给动画添加监听器
      * */
      Right_out.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationStart(Animator animation) {
              super.onAnimationStart(animation);
              card.setClickable(false);
          }
      });

      left_in.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              card.setClickable(true);
          }
      });

        linechart_left_in.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                card_lineChart.setClickable(false);
            }
        });

        linechart_right_out.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                card_lineChart.setClickable(true);
            }
        });
    }
}
