package com.ityingli.www.car1.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/4/26.
 */
public class DashBoardView_Two extends View {

   /*
   * 该自定义控件的宽和高
   * */
    private float mWidth;
    private float mHeight;
    private Paint drawCirclePaint;
    private Paint drawArcPaint;
    private Paint drawLinePaint;
    private Paint drawLineNumberPaint;
    private Paint drawCenterCirclePaint;

    /*
        * 构造方法
        * */
    public DashBoardView_Two(Context context) {
        this(context,null);
    }

    public DashBoardView_Two(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DashBoardView_Two(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
        * 初始化画笔
        * */
        initPaint();
    }

    private void initPaint() {
        //画一个大白圆 的画笔
        drawCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawCirclePaint.setColor(Color.parseColor("#FFFFFF"));
        drawCirclePaint.setStyle(Paint.Style.FILL);


        //画一个大圆弧的画笔
        drawArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawArcPaint.setColor(Color.parseColor("#1B1BF9"));
        drawArcPaint.setStrokeWidth(AdapterWidth(4));
        drawArcPaint.setStyle(Paint.Style.STROKE);

        //画线的笔
        drawLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawLinePaint.setColor(Color.parseColor("#1B1BF9"));
        drawLinePaint.setStrokeWidth(AdapterWidth(6));
        drawLinePaint.setStyle(Paint.Style.FILL);


        //换刻度数字的笔
        drawLineNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawLineNumberPaint.setColor(Color.parseColor("#1B1BF9"));
        drawLineNumberPaint.setStrokeWidth(AdapterWidth(4));
        drawLineNumberPaint.setStyle(Paint.Style.STROKE);

        //画圆心的笔，蓝色的圆圈，和红圆          。画文字的笔也是这
        drawCenterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawCenterCirclePaint.setStrokeWidth(AdapterWidth(4));
        drawCenterCirclePaint.setStyle(Paint.Style.STROKE);
        
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
        * 获取到模式和大少
        * */
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        /*
        * 通过判断模式来决定大少高度
        * */
        if(widthMode == MeasureSpec.EXACTLY){
            //match_parent或者精确值
            mWidth = widthSize;
        }else{

        }
        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = mWidth *0.8f;
        }else{

        }

        /*
        * 决定改控件的宽，高时刻
        * */
        setMeasuredDimension((int)mWidth,(int)mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 首先来画一个大圆圈
         */
        drawBigCircle(canvas);
        /*
         画一条圆弧
        */
        drawBigArc(canvas);
        /*
        * 画一条有颜色的弧线
        * */
      drawRainbowArc(canvas);
        /*
        * 画刻度线
        */
       drawScaleLine(canvas);
        /*
        * 画刻度数字
        * */
       drawScaleNumber(canvas);
         /*
         * 还圆心
         * */
       drawCenterCircle(canvas);

        /*
        * 画文字
        * */
        drawTextMethod(canvas);
    }

    private void drawTextMethod(Canvas canvas) {
        drawCenterCirclePaint.setColor(Color.parseColor("#1B1BF9"));
        drawCenterCirclePaint.setStyle(Paint.Style.FILL);
        drawCenterCirclePaint.setStrokeWidth(AdapterWidth(4));
        drawCenterCirclePaint.setTextSize(AdapterWidth(30));
        //KM/H
        float textWidth = drawCenterCirclePaint.measureText("KM/H");
        canvas.drawText("KM/H",getWidth()/2-(textWidth/2),AdapterWidth(120),drawCenterCirclePaint);
        //实时车速
        drawCenterCirclePaint.setStrokeWidth(AdapterWidth(8));
        drawCenterCirclePaint.setTextSize(AdapterWidth(50));
        float textWidth2 = drawCenterCirclePaint.measureText("实时车速");
        canvas.drawText("实时车速",getWidth()/2-(textWidth2/2),AdapterWidth(310),drawCenterCirclePaint);
    }

    private void drawCenterCircle(Canvas canvas) {
        drawCenterCirclePaint.setColor(Color.parseColor("#8F8FDC"));
        drawCenterCirclePaint.setStyle(Paint.Style.STROKE);
        drawCenterCirclePaint.setStrokeWidth(AdapterWidth(4));
        drawCenterCirclePaint.setColor(Color.parseColor("#1B1BF9"));
        canvas.drawCircle(AdapterWidth(240),AdapterWidth(192),AdapterWidth(12),drawCenterCirclePaint);
        //画红色的圆心
        drawCenterCirclePaint.setStyle(Paint.Style.FILL);
        drawCenterCirclePaint.setColor(Color.parseColor("#FF0000"));
        canvas.drawCircle(AdapterWidth(240),AdapterWidth(192),AdapterWidth(8),drawCenterCirclePaint);

        drawCenterCirclePaint.setStyle(Paint.Style.FILL);
        drawCenterCirclePaint.setStrokeWidth(AdapterWidth(4));
        //先画对准中间画指针
       // canvas.drawLine(AdapterWidth(240),AdapterWidth(192),getWidth()/2,AdapterWidth(30),drawCenterCirclePaint);
        //让画布旋转120度
        canvas.rotate(-120,AdapterWidth(240),AdapterWidth(192));
        canvas.drawLine(AdapterWidth(240),AdapterWidth(192),getWidth()/2,AdapterWidth(30),drawCenterCirclePaint);
        canvas.rotate(120,AdapterWidth(240),AdapterWidth(192));

    }

    private void drawRainbowArc(Canvas canvas) {
       Paint p = new Paint();
        //颜色数组
        int[] colors = {Color.parseColor("#F6BDB4"),Color.parseColor("#FFFFFF"),Color.parseColor("#39E11B"),Color.parseColor("#F9C8C2")};
        //颜色起点位置数组，从0-1      0.416666667f
        float []positions = {0,0.1f,0.4f,1f};
        SweepGradient sweepGradient2 = new SweepGradient(
                getWidth()/2,AdapterWidth(192),
                colors,positions
        );

        p.setStyle(Paint.Style.STROKE);   /*挺重要的一句*/
        p.setStrokeWidth(AdapterWidth(30));       /* 挺重要的一句*/
        p.setShader(sweepGradient2);

        canvas.drawArc(new RectF(AdapterWidth(97),AdapterWidth(49),AdapterWidth(383),AdapterWidth(335))
                ,150,240,false,p);

      //这里其实是分成四部分画了，粉色，白色，绿色，粉色
    }

    private void drawScaleNumber(Canvas canvas) {
        drawLineNumberPaint.setStrokeWidth(AdapterWidth(2));
        drawLineNumberPaint.setStyle(Paint.Style.FILL);
        drawLineNumberPaint.setTextSize(AdapterWidth(20));
        //画中间的数字
        int number = 120;
        String numberString = 120+"";
        float numberwidth= drawLineNumberPaint.measureText(numberString);
        canvas.drawText(numberString,getWidth()/2-(numberwidth/2),AdapterWidth(25),drawLineNumberPaint);


        //左边的文字
        for(int i=0;i<=5;i++){
            number = number-20;
            numberString = number+"";
            canvas.rotate(-20,getWidth()/2,AdapterWidth(192));
            canvas.drawText(numberString,getWidth()/2-numberwidth/2,AdapterWidth(25),drawLineNumberPaint);
        }

        //把画布复位
        canvas.rotate(120,getWidth()/2,AdapterWidth(192));


        number = 120;
        //右边的文字
        for(int i=0;i<=5;i++){
            number = number+20;
            numberString = number+"";
            canvas.rotate(20,getWidth()/2,AdapterWidth(192));
            canvas.drawText(numberString,getWidth()/2-numberwidth/2,AdapterWidth(25),drawLineNumberPaint);
        }
        canvas.rotate(-120,getWidth()/2,AdapterWidth(192));

    }

    private void drawScaleLine(Canvas canvas) {
       //画中间那条线
        drawLinePaint.setStrokeWidth(AdapterWidth(5));
        canvas.drawLine(getWidth()/2,AdapterWidth(30),getWidth()/2,AdapterWidth(55),drawLinePaint);

        //画右边的线,画布旋转，每次旋转20度

        for(int i=0;i<=5;i++){
            canvas.rotate(20,getWidth()/2,AdapterWidth(192));
            canvas.drawLine(getWidth()/2,AdapterWidth(30),getWidth()/2,AdapterWidth(55),drawLinePaint);
        }

        //画布旋转回原位
        canvas.rotate(-120,getWidth()/2,AdapterWidth(192));

        //画左边的刻度
        for(int i=0;i<=5;i++){
            canvas.rotate(-20,getWidth()/2,AdapterWidth(192));
            canvas.drawLine(getWidth()/2,AdapterWidth(30),getWidth()/2,AdapterWidth(55),drawLinePaint);
        }

        //画布旋转回原位
        canvas.rotate(120,getWidth()/2,AdapterWidth(192));
    }

    private void drawBigArc(Canvas canvas) {
        drawArcPaint.setStrokeWidth(AdapterWidth(4));
        canvas.drawArc(new RectF(AdapterWidth(80),AdapterWidth(32),AdapterWidth(400),AdapterWidth(352)),150,240,false,drawArcPaint);
    }


    private void drawBigCircle(Canvas canvas) {
        canvas.drawCircle(AdapterWidth(240),AdapterWidth(192),AdapterWidth(160),drawCirclePaint);
    }

    public float AdapterWidth(float number){
        float endnumber;
        float scale = 480/number;
        endnumber = getWidth()/scale;
        return endnumber;
    }
}
