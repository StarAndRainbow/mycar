package com.ityingli.www.car1.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MyLineChartView  extends View {

    /*
    * 坐标轴x，y的原点位置
    * */
    // private int xPoint = (int)AdapterLocalAndWidth(50);
    // private int yPoint = (int)AdapterLocalAndWidth(120);
    private float xPoint = AdapterLocalAndWidth(50);
    private float yPoint = AdapterLocalAndWidth(120);
    /*

    /*
    * 空间的宽和高
    * */
    private float mViewWidth;
    private float mViewheight;
    /*
    * 画笔
    * */
    private Paint mpaint;
    /*
    * 刻度的文字
    * */
    List<Integer> scaleNumbers = new ArrayList<>();
    /*
    * 需要绘制的点
    * */
    List<Integer> datas = new ArrayList<>();

    /*隔5个刻度画一条线
    * */
    private float XScale = 5;
    private float YScale = 1;
    /*
    * 最多可以绘制多少个点
    * */
    private int MaxPoint = (int)(190/XScale);

    /*
    * 接收到消息的时候就刷新视图
    * */
    private static int MESSAGEFLAG = 1;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MESSAGEFLAG){
                MyLineChartView.this.invalidate();
            }
        }
    };
    /*
    * 构造方法
    * */
    public MyLineChartView(Context context) {
        this(context,null);
    }

    public MyLineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inintPaint();
        initDatas();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (datas.size() > MaxPoint) {
                        datas.remove(0);
                    }
                    datas.add(new Random().nextInt(80)); //生成0-80的点  datas.add(new Random().nextInt(80)+1); //生成1-80的点
                    handler.sendEmptyMessage(MESSAGEFLAG);
                }
            }
        }).start();

    }

    /*
    * 初始化数据
    * */
    private void initDatas() {
        int number = 00;
        for(int i = 0 ;i<5;i++){
            scaleNumbers.add(number);
            number+=20;
        }
       /*
        //先给datas模拟一点数据测试
        for(int i = 0 ;i<10;i++){
        datas.add(10*i);
        }*/

    }


    /*
    * 初始化画笔
    * */
    private void inintPaint() {
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);    //锯刺
        mpaint.setColor(Color.parseColor("#0000FF")); //颜色
        mpaint.setStrokeWidth(AdapterLocalAndWidth(5));  //线条大少
        mpaint.setStyle(Paint.Style.FILL);                //画笔风格
    }

    /*
    * 测量
    * */


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            mViewWidth = widthSize;
        }
        if(heightMode == MeasureSpec.EXACTLY ){
            mViewheight = heightSize;
        }

        setMeasuredDimension((int)mViewWidth,(int)mViewheight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // Toast.makeText(getContext(),"width:"+mViewWidth,Toast.LENGTH_LONG).show();   //240
        //Toast.makeText(getContext(),"height:"+mViewheight,Toast.LENGTH_LONG).show();  //155
        //画原点
        //canvas.drawCircle(AdapterLocalAndWidth(50),AdapterLocalAndWidth(120),AdapterLocalAndWidth(8),mpaint);
        //画x轴     240-50 = 190
        mpaint.setStrokeWidth(AdapterLocalAndWidth(2));  //线条大少
        canvas.drawLine(AdapterLocalAndWidth(50),AdapterLocalAndWidth(120),AdapterLocalAndWidth(240),AdapterLocalAndWidth(120),mpaint);
        //画y轴    120 - 20 = 100
        canvas.drawLine(AdapterLocalAndWidth(50),AdapterLocalAndWidth(120),AdapterLocalAndWidth(50),AdapterLocalAndWidth(20),mpaint);
        //画y轴左右两边的及的箭头
        canvas.drawLine(AdapterLocalAndWidth(50),AdapterLocalAndWidth(20),AdapterLocalAndWidth(40),AdapterLocalAndWidth(30),mpaint);
        canvas.drawLine(AdapterLocalAndWidth(50),AdapterLocalAndWidth(20),AdapterLocalAndWidth(60),AdapterLocalAndWidth(30),mpaint);

        //画y轴的刻度,画5个
        float yscale = AdapterLocalAndWidth(100/5);
        //第一个是在原点的,度数也为原点
        float scale = AdapterLocalAndWidth(120);
        for(int i = 0 ;i<5;i++){
            canvas.drawLine(AdapterLocalAndWidth(50),scale,AdapterLocalAndWidth(60),scale,mpaint);
            scale = scale-yscale;
        }

        mpaint.setTextSize(AdapterLocalAndWidth(20));
        //刻度画文字 , 10的间隔
        float textwidth = mpaint.measureText("00");
        float textHeight = MeasureTextHeight(mpaint);
        textwidth = AdapterLocalAndWidth(textwidth);
        scale = AdapterLocalAndWidth(120);
        for(int i = 0; i<5; i++) {
            /// canvas.drawText(scaleNumbers.get(i) + "", AdapterLocalAndWidth(50 - (textwidth + 10)), scale + textHeight / 2, mpaint);
            canvas.drawText(scaleNumbers.get(i) + "", AdapterLocalAndWidth(50)-textwidth/2-AdapterLocalAndWidth(15), scale+(textHeight/2), mpaint);
            scale = scale -yscale;
        }

        /*
        * 两个点才可以开始画线
        * */
        //实现填充
        mpaint.setStrokeWidth(AdapterLocalAndWidth(3));

        //坐标轴的起点
        float x = AdapterLocalAndWidth(50);
        float y = AdapterLocalAndWidth(120);
        float x_scale = AdapterLocalAndWidth(5);
        float y_scale = AdapterLocalAndWidth(1);
        if(datas.size()>1){
            for(int i = 1;i<datas.size();i++){
                //  canvas.drawLine(xPoint+(i-1)*XScale,yPoint-datas.get(i-1)*YScale,xPoint+i*XScale,yPoint-datas.get(i)*YScale,mpaint);
                canvas.drawLine(x+(i-1)*x_scale,y-datas.get(i-1)*y_scale,x+i*x_scale,y-datas.get(i)*y_scale,mpaint);
            }
        }

        //画当前发动机转速
        mpaint.setTextSize(AdapterLocalAndWidth(18));
        float tvWidth = mpaint.measureText("当前发动机转速R/Min");
        canvas.drawText("当前发动机转速R/Min", AdapterLocalAndWidth(50), AdapterLocalAndWidth(140), mpaint);

    }


    /*
    * 百分比方法，测试用的是480*800的机子
    * */
    private float AdapterLocalAndWidth(float value){
        float number;
        float scale;
        scale = 240/value;
        number = mViewWidth/scale;
        return number;
    }


    /*
    * 文字高度的测量
    * */
    private float  MeasureTextHeight(Paint mpaint){
        Paint.FontMetrics fontMetrics = mpaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

}
