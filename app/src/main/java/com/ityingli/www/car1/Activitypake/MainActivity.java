package com.ityingli.www.car1.Activitypake;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityingli.www.car1.Adapter.MyPageAdapter;
import com.ityingli.www.car1.Pager.HomePager;
import com.ityingli.www.car1.Pager.HomePager1;
import com.ityingli.www.car1.Pager.HomePager2;
import com.ityingli.www.car1.Pager.LeftFragment;
import com.ityingli.www.car1.Pager.MyPager;
import com.ityingli.www.car1.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    //viewPager
    private List<Fragment> pagerDatas;
    private TextView toptitile_id;


    private LinearLayout ll1;
    private ImageView imag1;
    private LinearLayout ll2;
    private ImageView img2;
    private TextView tv2;
    private LinearLayout ll3;
    private ImageView img3;
    private TextView tv3;
    private LinearLayout ll4;
    private ImageView imag4;
    private TextView tv4;
    private TextView tv_1;
    private String language;

    //sharedPreferences
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    Resources resoureces ;
    Configuration configuration ;
    DisplayMetrics displayMetrics;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        //viewPager
        initPagerDatas();
        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(),pagerDatas));
        viewPager.setOnPageChangeListener(this);
        imag1.setImageResource(R.drawable.ic_tab_one);
        tv_1.setTextColor(this.getResources().getColor(R.color.myblue));
        initDatas();
       setAppLanguage();
        //刚进来的时候标题不会切换不会切换语言,解决语言不会切换问题
        solveLanguageProblem();
        /*
        *侧滑
        * */
        setSlideingMenu();
        /*
        * 如果有导航栏或者虚拟按钮直接往上顶
        * */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       }

    private void setSlideingMenu() {
        //侧滑
        LeftFragment leftFragment  =  new LeftFragment();
        setBehindContentView(R.layout.left_franglayout);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_frameLayout,leftFragment).commit();
        final SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingMenu_offset);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slidingMenu.isMenuShowing())
                 slidingMenu.toggle();
            }
        });
    }

    private void solveLanguageProblem() {
        toptitile_id.setText(getResources().getString(R.string.index));
        tv_1.setText(getResources().getString(R.string.index));
        tv2.setText(getResources().getString(R.string.free));
        tv3.setText(getResources().getString(R.string.commodity));
        tv4.setText(getResources().getString(R.string.my));
    }

    private void setAppLanguage() {
        SharedPreferences sp = getSharedPreferences("isFirst",MODE_PRIVATE);
        String isFirst = sp.getString("Language","isFrist");
        Toast.makeText(MainActivity.this, "--"+isFirst, Toast.LENGTH_SHORT).show();
        //安装好程序后如果是第一次启动,那就根据系统语言选择
        if(isFirst.equals("isFrist")){
            getAppLanguage();
            showApplanguage();    //第一次启动根据系统语言为用户设定语言
        }else if(isFirst.equals("0")){
               //跟随系统设定
            Locale locale  = Locale.getDefault();
            language = locale.getCountry();
            if(language.equals("Tw")){
                configuration.locale = Locale.TAIWAN;//繁体中文
                Toast.makeText(MainActivity.this,"繁体",Toast.LENGTH_SHORT).show();
            }else if(language.equals("UK")|language.equals("US")  ){
                configuration.locale = Locale.ENGLISH;    //英文
                Toast.makeText(MainActivity.this,"英文",Toast.LENGTH_SHORT).show();
            }else if(language.equals("CN")){
                configuration.locale = Locale.SIMPLIFIED_CHINESE;//简体中文
                Toast.makeText(MainActivity.this,"中文",Toast.LENGTH_SHORT).show();
            }
            resoureces.updateConfiguration(configuration,displayMetrics);
        }else if(isFirst.equals("1")){
            configuration.locale = Locale.SIMPLIFIED_CHINESE;//简体中文

        }else if(isFirst.equals("2")){
            configuration.locale = Locale.TAIWAN;//繁体中文
        }else if(isFirst.equals("3")){
            configuration.locale = Locale.ENGLISH;
        }
        resoureces.updateConfiguration(configuration,displayMetrics);
    }

    private void initDatas() {
        sharedPreferences = getSharedPreferences("isFirst",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.commit();
        resoureces = getResources();
         configuration = resoureces.getConfiguration();
         displayMetrics= resoureces.getDisplayMetrics();
    }

    //根据系统语言，设置app的语言
    private void showApplanguage() {
          if(language.equals("TW")){
             configuration.locale = Locale.TAIWAN;//繁体中文
             editor.putString("Language","2");//并且把他设置为跟系统默认,并且我们把他保存进去，以后系统改语言的时候，我们的语言照样不会改动
              editor.commit();
              Toast.makeText(this,"当前语言为:"+language,Toast.LENGTH_SHORT).show();
          }else if(language.equals("CA") |language.equals("US")  ){
             configuration.locale = Locale.ENGLISH;    //英文
             editor.putString("Language","3");                             //并且把他设置为跟系统默认
              editor.commit();
              Toast.makeText(this,"当前语言为:"+language,Toast.LENGTH_SHORT).show();
          }else if(language.equals("CN")){
             configuration.locale = Locale.SIMPLIFIED_CHINESE;//简体中文
             editor.putString("Language","1");                              //并且把他设置为跟系统默认
              editor.commit();
              Toast.makeText(this,"当前语言为:"+language,Toast.LENGTH_SHORT).show();
          }
        resoureces.updateConfiguration(configuration,displayMetrics);
    }

    private void getAppLanguage() {
        //获取系统当前的语言
        Locale locale  = Locale.getDefault();
        //language = locale.getLanguage();
        language = locale.getCountry();
        //英语的时候显示CA 或者US
        //简体为CN
        //繁体的时候为TW
    }


    private void initPagerDatas(){
        pagerDatas = new ArrayList<>();
        HomePager homePager = new HomePager();
        HomePager1 homePager1 = new HomePager1();
        HomePager2 homePager2 = new HomePager2();
        MyPager homePager3 = new MyPager();
        pagerDatas.add(homePager);
        pagerDatas.add(homePager1);
        pagerDatas.add(homePager2);
        pagerDatas.add(homePager3);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPaer);
        toptitile_id = (TextView) findViewById(R.id.titile_id);
        //bottom
        ll1 = (LinearLayout)findViewById( R.id.ll_1 );
        tv_1 = (TextView) findViewById(R.id.tv_1);
        imag1 = (ImageView)findViewById( R.id.imag_1 );
        ll2 = (LinearLayout)findViewById( R.id.ll_2 );
        img2 = (ImageView)findViewById( R.id.img2 );
        tv2 = (TextView)findViewById( R.id.tv2 );
        ll3 = (LinearLayout)findViewById( R.id.ll_3 );
        img3 = (ImageView)findViewById( R.id.img3 );
        tv3 = (TextView)findViewById( R.id.tv3 );
        ll4 = (LinearLayout)findViewById( R.id.ll_4 );
        imag4 = (ImageView)findViewById( R.id.imag4 );
        tv4 = (TextView)findViewById( R.id.tv4 );

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        ll4.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        System.out.println(view.getId());
        resetting();
        switch (view.getId()){
            case R.id.ll_1:
                viewPager.setCurrentItem(0);
                 imag1.setImageResource(R.drawable.ic_tab_one);
                tv_1.setTextColor(this.getResources().getColor(R.color.myblue));
                break;
            case R.id.ll_2:
                viewPager.setCurrentItem(1);
                img2.setImageResource(R.drawable.ic_tab_two);
                tv2.setTextColor(this.getResources().getColor(R.color.myblue));
                break;
            case R.id.ll_3:
                viewPager.setCurrentItem(2);
                img3.setImageResource(R.drawable.ic_tab_three);
              tv3.setTextColor(this.getResources().getColor(R.color.myblue));
                break;
            case R.id.ll_4:
                viewPager.setCurrentItem(3);
                imag4.setImageResource(R.drawable.ic_tab_four);
               tv4.setTextColor(this.getResources().getColor(R.color.myblue));
                break;
        }
    }

    public void resetting(){
        imag1.setImageResource(R.drawable.ic_tab_one_ccc);
       tv_1.setTextColor(this.getResources().getColor(R.color.mygray));
        img2.setImageResource(R.drawable.ic_tab_two_ccc);
        tv2.setTextColor(this.getResources().getColor(R.color.mygray));
        img3.setImageResource(R.drawable.ic_tab_three_ccc);
       tv3.setTextColor(this.getResources().getColor(R.color.mygray));
        imag4.setImageResource(R.drawable.ic_tab_four_ccc);
        tv4.setTextColor(this.getResources().getColor(R.color.mygray));
    }


    //pager页卡事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MyApplication.setMyposition(position);
        resetting();
       switch (position){
           case 0:
               imag1.setImageResource(R.drawable.ic_tab_one);
               tv_1.setTextColor(this.getResources().getColor(R.color.myblue));
               toptitile_id.setText(getResources().getString(R.string.index));
               getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
               break;
           case 1:
               img2.setImageResource(R.drawable.ic_tab_two);
               tv2.setTextColor(this.getResources().getColor(R.color.myblue));
               toptitile_id.setText(getResources().getString(R.string.free));
               /*
               * 设置侧滑触摸不起作用
               * */
               getSlidingMenu().setTouchModeAbove(
                       SlidingMenu.TOUCHMODE_NONE);
               break;
           case 2:
               img3.setImageResource(R.drawable.ic_tab_three);
               tv3.setTextColor(this.getResources().getColor(R.color.myblue));
               toptitile_id.setText(getResources().getString(R.string.commodity));
               getSlidingMenu().setTouchModeAbove(
                       SlidingMenu.TOUCHMODE_NONE);
               break;
           case 3:
               imag4.setImageResource(R.drawable.ic_tab_four);
               tv4.setTextColor(this.getResources().getColor(R.color.myblue));
               toptitile_id.setText(getResources().getString(R.string.my));
               getSlidingMenu().setTouchModeAbove(
                       SlidingMenu.TOUCHMODE_NONE);
               break;

       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.sett1IsStart(false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}







