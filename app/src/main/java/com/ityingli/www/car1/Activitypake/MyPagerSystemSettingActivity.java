package com.ityingli.www.car1.Activitypake;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ityingli.www.car1.R;
import com.ityingli.www.car1.Util.GlideCacheUtil;
import com.ityingli.www.car1.View.SwitchView;

import java.util.Locale;

/**
 * Created by Administrator on 2017/4/20.
 */
public class MyPagerSystemSettingActivity  extends Activity implements View.OnClickListener {
    private ImageButton imgButton_back;
    /*
    * 选中语言的控件
    * */
    private LinearLayout follow_system_language;
    private LinearLayout simplifi_chinese;
    private LinearLayout chinese;
    private LinearLayout englise;
    private ImageView  follow_system_language_img;
    private ImageView  simplified_chinese_language_img;
    private ImageView  chinese_img;
    private ImageView  englise_img;
    /*
    *记录之前被选中的语言位置,把其位置模拟为0,1,2,3,4
    * */
    private int selectedLanguagePosition ;
    /*
    * 当前选种的位置
    * */
    private int selectingLanguagePosition;
    //设置语种要用到的属性
    private Resources resources;
    private Configuration configuration;
    private DisplayMetrics displayMetrics;
    private String language;
    //数据保存
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    /*
    * 设置点击的时候，仿ios按钮改变
    * */
    private LinearLayout network_linealayout;
    private LinearLayout blueboot_linealayout;
    private SwitchView network_button;
    private SwitchView blueboot_button;
    private boolean network_isOn;
    private boolean blueboot_isOn2;
    /*
    * 缓存
    * */
    private LinearLayout cacle;
    private GlideCacheUtil glideCacheUtil;
    private TextView cacle_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mypager_systemsetting_layout);

        /*
        * 初始化控件
        * */
        initView();
        /*
        * 初始化值
        * */
        initNumber();
        /*
        * 初始化事件
        * */
        initEvent();
        /*
        * 判断选中的语言，把图标设置为显示
        * */
        showCorrent(selectingLanguagePosition);
        /*
        * 显示缓存的大少
        * */
        showCacheSize();
    }

    private void showCacheSize() {
        glideCacheUtil = new GlideCacheUtil();
        cacle_size.setText(glideCacheUtil.getCacheSize(MyPagerSystemSettingActivity.this));
    }

    private void initNumber() {
        resources = getResources();
        configuration = resources.getConfiguration();
        displayMetrics = resources.getDisplayMetrics();

        sp = getSharedPreferences("isFirst",MODE_PRIVATE);
        editor = sp.edit();
        String isFirst = sp.getString("Language","isFrist");
        selectedLanguagePosition = Integer.parseInt(isFirst);
        selectingLanguagePosition = Integer.parseInt(isFirst);
    }

    private void initEvent() {
        imgButton_back.setOnClickListener(this);
        follow_system_language.setOnClickListener(this);
        simplifi_chinese.setOnClickListener(this);
        chinese.setOnClickListener(this);
        englise.setOnClickListener(this);

        network_linealayout.setOnClickListener(this);
        blueboot_linealayout.setOnClickListener(this);

        cacle.setOnClickListener(this);
    }

    private void initView() {
        imgButton_back = (ImageButton) findViewById(R.id.back_id);
        follow_system_language = (LinearLayout) findViewById(R.id.follow_system_language);
        simplifi_chinese = (LinearLayout) findViewById(R.id.simplified_chinese);
        chinese = (LinearLayout) findViewById(R.id.chinese);
        englise = (LinearLayout) findViewById(R.id.English);

        follow_system_language_img = (ImageView) findViewById(R.id.allow_system_language_img);
        simplified_chinese_language_img = (ImageView) findViewById(R.id.simplified_language_img);
        chinese_img  = (ImageView) findViewById(R.id.chinese_img);
        englise_img = (ImageView) findViewById(R.id.englise_img);

        network_linealayout = (LinearLayout) findViewById(R.id.network_lineaLayout_id);
        blueboot_linealayout= (LinearLayout) findViewById(R.id.blueboot_lineaLayout_id);
        network_button = (SwitchView) findViewById(R.id.network_button_id);
        blueboot_button = (SwitchView) findViewById(R.id.blueboot_button_id);

        //缓存
        cacle = (LinearLayout)findViewById(R.id.cacle);
        cacle_size = (TextView)findViewById(R.id.cacle_size);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_id:
                 finish();
                break;
            case R.id.follow_system_language:
                selectingLanguagePosition = 0;
                //设置隐藏图片和显示图片和设置语言切换
                setCorrentShowOrHiddenIconAndsetLanguage();
                break;
            case R.id.simplified_chinese:
                selectingLanguagePosition = 1;
                setCorrentShowOrHiddenIconAndsetLanguage();
                break;
            case R.id.chinese:
                selectingLanguagePosition = 2;
                setCorrentShowOrHiddenIconAndsetLanguage();
                break;
            case R.id.English:
                selectingLanguagePosition = 3;
                setCorrentShowOrHiddenIconAndsetLanguage();
                break;
            case R.id.network_lineaLayout_id:
               network_isOn = network_button.getIsOn();
                network_button.setIsOn(!network_isOn);
                break;
            case R.id.blueboot_lineaLayout_id:
                blueboot_isOn2 = blueboot_button.getIsOn();
                blueboot_button.setIsOn(!blueboot_isOn2);
                break;
            case  R.id.cacle:
                glideCacheUtil .clearImageAllCache(MyPagerSystemSettingActivity.this);
                Toast.makeText(MyPagerSystemSettingActivity.this,"正在清理缓存",Toast.LENGTH_SHORT).show();
                cacle_size.setText(glideCacheUtil.getCacheSize(MyPagerSystemSettingActivity.this));
                break;
        }
    }


    //设置隐藏图片和显示图片和设置语言切换

    private void setCorrentShowOrHiddenIconAndsetLanguage() {
        //隐藏之前选中的图片
        hiddCorrent(selectedLanguagePosition);
        //显示选中的图标
        showCorrent(selectingLanguagePosition);
        //如果之前选中的位置和用户现在选中的位置相同，则不用设置语言
        if(selectedLanguagePosition ==selectingLanguagePosition){
            Toast.makeText(MyPagerSystemSettingActivity.this,"语言种类没做更改",Toast.LENGTH_SHORT).show();
        }else{
            //设置位置
            selectedLanguagePosition = selectingLanguagePosition;
            //设置语言
          setLanguage(selectingLanguagePosition);
        }
    }


    /*
    * 设置语言显示
    * */
    private void setLanguage(int selectingLanguagePosition) {
        switch (selectingLanguagePosition){
            case 0:
                editor.putString("Language","0");//并且把他设置为跟系统默认,并且我们把他保存进去，以后系统改语言的时候，我们的语言照样不会改动
                editor.commit();
                Toast.makeText(MyPagerSystemSettingActivity.this,"跟随系统的语言",Toast.LENGTH_SHORT).show();
                Locale locale  = Locale.getDefault();
                language = locale.getCountry();
                if(language.equals("TW")){
                    configuration.locale = Locale.TAIWAN;//繁体中文
                }else if(language.equals("CA")  ){
                    configuration.locale = Locale.ENGLISH;    //英文
                }else if(language.equals("CN")){
                    configuration.locale = Locale.SIMPLIFIED_CHINESE;//简体中文
                }
                break;
            case 1:
                Toast.makeText(MyPagerSystemSettingActivity.this,"简体中文",Toast.LENGTH_SHORT).show();
                configuration.locale = Locale.SIMPLIFIED_CHINESE;//简体中文
                editor.putString("Language","1");//并且把他设置为跟系统默认,并且我们把他保存进去，以后系统改语言的时候，我们的语言照样不会改动
                editor.commit();
                break;
            case 2:
                Toast.makeText(MyPagerSystemSettingActivity.this,"繁体中文",Toast.LENGTH_SHORT).show();
                configuration.locale = Locale.TAIWAN;//繁体中文
                editor.putString("Language","2");//并且把他设置为跟系统默认,并且我们把他保存进去，以后系统改语言的时候，我们的语言照样不会改动
                editor.commit();
                break;
            case 3:
                Toast.makeText(MyPagerSystemSettingActivity.this,"英语",Toast.LENGTH_SHORT).show();
                configuration.locale = Locale.ENGLISH;    //英文
                editor.putString("Language","3");//并且把他设置为跟系统默认,并且我们把他保存进去，以后系统改语言的时候，我们的语言照样不会改动
                editor.commit();
                break;
        }
        //这里是真正的更新
        resources.updateConfiguration(configuration,displayMetrics);
        //更新语言之后，要跳到主界面才会更新
        Intent intent = new Intent(MyPagerSystemSettingActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    /*
    * 隐藏之前语言选中的位置
    * */
    private void hiddCorrent(int selectedLanguagePosition) {
        switch (selectedLanguagePosition){
            case 0:
                follow_system_language_img.setVisibility(View.GONE);
                break;
            case 1:
                simplified_chinese_language_img.setVisibility(View.GONE);
                break;
            case 2:
                chinese_img.setVisibility(View.GONE);
                break;
            case 3:
                englise_img.setVisibility(View.GONE);
                break;
        }
    }

    /*
    * 设置选中语言的图标correct显示
    * */
    private void showCorrent(int selectingLanguagePosition) {
        switch (selectingLanguagePosition){
            case 0:
                follow_system_language_img.setVisibility(View.VISIBLE);
                break;
            case 1:
                simplified_chinese_language_img.setVisibility(View.VISIBLE);
                break;
            case 2:
                chinese_img.setVisibility(View.VISIBLE);
                break;
            case 3:
                englise_img.setVisibility(View.VISIBLE);
                break;
        }
    }


}
