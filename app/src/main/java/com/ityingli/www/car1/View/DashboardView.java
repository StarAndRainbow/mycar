package com.ityingli.www.car1.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;



/**
 * Created by Administrator on 2017/4/16.
 */
public class DashboardView extends View {
    private static final String TAG = "abc";

    //外圆的笔
    Paint exeirclePaint;

    //画内圆的笔
    Paint insidePaint;
    //画文字的笔和属性
    Paint textPaint;



    //刻度的表，宽和长度，和颜色
    Paint scalePaint;


    //98字样s属性
    private Paint paint_98;

    //98下面一行字的属性， 第二的画笔属性等等都用和刻度一样做简单处理算了(此处的属性和第二行无关)
    private Paint targetTextPaint;


    //第二行的属性
    private Paint towTargerPaint;


    //底部文字
    private Paint bottomPaint;

    //仪表盘控件的宽高
    float mWidth;
    float mHeight;

    Context mcontext = null;


    //让外圆动起来的变量，笔和画
    Paint updataPaint;
    float  updataNumber;
    int  textNumber;

    public void setUpdataNumber(float number){
        updataNumber = number;
    }

    //设置文字动起来
    public void updataTextNumber(int number){
        textNumber = number;
    }


    public DashboardView(Context context) {
        this(context,null);
    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        initPaint();
    }




    private void initPaint() {
        //外圆的画笔
        exeirclePaint = new Paint();
        exeirclePaint.setAntiAlias(true);                         //抗锯刺
        exeirclePaint.setStyle(Paint.Style.STROKE);//空心，实心，空心和实心
        exeirclePaint.setStrokeWidth(AdapterWidth(5));
        exeirclePaint.setColor(Color.parseColor("#4FC0EF"));


        //让外圆动起来的笔
        updataPaint = new Paint();
        updataPaint.setAntiAlias(true);                         //抗锯刺
        updataPaint.setStyle(Paint.Style.STROKE);//空心，实心，空心和实心
        updataPaint.setColor(Color.parseColor("#ffffff"));


        //内圆的画笔
        insidePaint = new Paint();
        insidePaint.setAntiAlias(true);
        insidePaint.setStyle(Paint.Style.STROKE);
        insidePaint.setColor(Color.parseColor("#4FC0EF"));


        //刻度的画笔
        scalePaint = new Paint();
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setColor(Color.parseColor("#FFFFFF"));


        //画刻度文字的笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        scalePaint.setColor(Color.parseColor("#FFFFFF"));



        //98的笔
        paint_98 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_98.setAntiAlias(true);
        paint_98.setStyle(Paint.Style.FILL);


        //98下面一行文字的笔
        targetTextPaint = new Paint();
        targetTextPaint.setAntiAlias(true);
        targetTextPaint.setStyle(Paint.Style.FILL);
        targetTextPaint.setColor(Color.parseColor("#ffffff"));

        //第二行
        towTargerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        towTargerPaint.setTextSize(30);
        towTargerPaint.setColor(Color.parseColor("#FFFFFF"));
        towTargerPaint.setStyle(Paint.Style.FILL);

        //底部的笔
        bottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomPaint.setTextSize(40);
        bottomPaint.setColor(Color.parseColor("#FFFFFF"));
        bottomPaint.setStyle(Paint.Style.FILL);



    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hegiht = MeasureSpec.getSize(heightMeasureSpec);
        int hegihtmode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode == MeasureSpec.EXACTLY){

            mWidth = width;
        }else{
            //其实这里没有用到

        }

        if(hegihtmode == MeasureSpec.EXACTLY){
            mHeight = width*0.8f;
        }else{
          //  mHeight = px2dip(mcontext,300);        //用300dp
            //如果没有设置高度的话，那就是跨度的一点三倍
           // mHeight = mWidth * 1.3f;
        }
         setMeasuredDimension((int)mWidth,(int)mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //画外圆
        drawExcircle(canvas);

        //让外圆动起来
        drawUpdataExcircle(canvas);

        //画内圆
       drawInside(canvas);
        //画刻度
      drawScale(canvas);
        //画刻度文字
        drawTextdesc(canvas);
        //画98和下面的方法
        drawText_98(canvas);
        //98下面的文字描述
       drawTarger(canvas);
        //底部文字
       drawBottomText(canvas);

        super.onDraw(canvas);
    }



    private void drawBottomText(Canvas canvas) {
        String center1 = "5.89";
        String center2 ="今日耗油";

        String leftText1 ="0.00";
        String leftText2 ="车速KM/H";

        String rightText1 ="159.5";
        String rightText2 = "今日里程";
        bottomPaint.setTextSize(AdapterWidth(30));
        float center1Width1 = bottomPaint.measureText(center1);
        float center2Width2 = bottomPaint.measureText(center2);
        canvas.drawText(center1,(mWidth-center1Width1)/2,AdapterHeight(690),bottomPaint);
        canvas.drawText(center2,(mWidth-center2Width2)/2,AdapterHeight(730),bottomPaint);

        canvas.save();
        bottomPaint.setTextSize(AdapterWidth(40));
        //画两条线
        canvas.drawLine(mWidth/2-mWidth/2/4,AdapterHeight(680),mWidth/2-mWidth/2/4,AdapterHeight(740),bottomPaint);
        canvas.drawLine(mWidth/2+mWidth/2/4,AdapterHeight(680),mWidth/2+mWidth/2/4,AdapterHeight(740),bottomPaint);

        //左边
        float leftTextWidth1 = bottomPaint.measureText(leftText1);
        float leftTextWidth2 = bottomPaint.measureText(leftText2);
        canvas.drawText(leftText1,((mWidth/2-mWidth/2/4)-leftTextWidth1)/2+AdapterWidth(60),AdapterHeight(690),bottomPaint);
        canvas.drawText(leftText2,((mWidth/2-mWidth/2/4)-leftTextWidth2)/2+AdapterWidth(60),AdapterHeight(730),bottomPaint);

       //右边
        float rightTextWidth1 = bottomPaint.measureText(rightText1);
        float rightTextWidth2 = bottomPaint.measureText(rightText2);

        canvas.drawText(rightText1,AdapterWidth(1080-300),AdapterHeight(690),bottomPaint);
        canvas.drawText(rightText2,AdapterWidth(1080 -320),AdapterHeight(730),bottomPaint);




    }

    private void drawTarger(Canvas canvas) {
        targetTextPaint.setTextSize(AdapterWidth(40));
        String text ="车辆健康指数";
        float textwidth= targetTextPaint.measureText(text);
        canvas.drawText(text,(mWidth-textwidth)/2,AdapterHeight(520),targetTextPaint);

        towTargerPaint.setColor(Color.parseColor("#ffffff"));
        towTargerPaint.setTextSize(AdapterWidth(30));
        String twotext = "体验时间:2017/4/15";
        textwidth = towTargerPaint.measureText(twotext);
        canvas.drawText(twotext,(mWidth-textwidth)/2,AdapterHeight(560),towTargerPaint);
    }

    private void drawText_98(Canvas canvas) {
        paint_98.setTextSize(AdapterWidth(200));
        paint_98.setColor(Color.parseColor("#ffffff"));
        //canvas.drawText("98",getWidth()/2-(paint_98.measureText("98"))/2,AdapterHeight(450),paint_98);
        canvas.drawText(textNumber+"",getWidth()/2-(paint_98.measureText(textNumber+""))/2,AdapterHeight(450),paint_98);

    }

    private void drawTextdesc(Canvas canvas) {
       //先画中间文字
       int scaleNumber = 50;
       String text = scaleNumber+"";
       float textwidth = textPaint.measureText(text);
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setTextSize(AdapterWidth(30));
        canvas.drawText(text,AdapterWidth((1080-textwidth)/2),AdapterHeight(210),textPaint);

       //右边的文字
        for(int i = 0 ;i<5;i++){
            canvas.rotate(20,AdapterWidth(540),AdapterWidth(540));
            scaleNumber+=10;
            text = scaleNumber+"";
            canvas.drawText(text,AdapterWidth((1080-textwidth)/2),AdapterHeight(210),textPaint);
        }


        //把画布旋转回来
        canvas.rotate(-100,AdapterWidth(540),AdapterWidth(540));

        //左边
        scaleNumber = 50;
        for(int i = 0 ;i<5;i++){
            canvas.rotate(-20,AdapterWidth(540),AdapterWidth(540));
            scaleNumber-=10;
            text = scaleNumber+"";
            canvas.drawText(text,AdapterWidth((1080-textwidth)/2),AdapterHeight(210),textPaint);
        }

        //画布旋转回去
        canvas.rotate(100,AdapterWidth(540),AdapterWidth(540));

   }

    private void drawScale(Canvas canvas) {
        scalePaint.setStrokeWidth(AdapterWidth(10));
        //现在中间画一个刻度
        canvas.drawLine(AdapterWidth(540),AdapterWidth(120), AdapterWidth(540),AdapterWidth(180),scalePaint);
        //旋转，画右边的刻度 (每一个刻度旋转的度数)
       for(int i = 0 ;i<5;i++){
           canvas.rotate(20,AdapterWidth(540),AdapterWidth(540));
           canvas.drawLine(AdapterWidth(540),AdapterWidth(120), AdapterWidth(540),AdapterWidth(180),scalePaint);
        }
        //把画布旋转回来
        canvas.rotate(-100,AdapterWidth(540),AdapterWidth(540));

        //左边

        for(int i = 0 ;i<5;i++){
            canvas.rotate(-20,AdapterWidth(540),AdapterWidth(540));
            canvas.drawLine(AdapterWidth(540),AdapterWidth(120), AdapterWidth(540),AdapterWidth(180),scalePaint);
        }
        //画布旋转回去
        canvas.rotate(100,AdapterWidth(540),AdapterWidth(540));
    }

    private void drawInside(Canvas canvas) {
        insidePaint.setStrokeWidth(AdapterWidth(60));
     canvas.drawArc(new RectF(AdapterWidth(150),AdapterWidth(150),AdapterWidth(930),AdapterWidth(930)),170,200,false,insidePaint);
    }


    //让外圆动起来
    private void drawUpdataExcircle(Canvas canvas) {
        updataPaint.setStrokeWidth(AdapterWidth(20));
        canvas.drawArc(new RectF(AdapterWidth(80),AdapterWidth(80), AdapterWidth(1000), AdapterWidth(1000)),170,updataNumber,false,updataPaint);
    }




    private void drawExcircle(Canvas canvas) {
        exeirclePaint.setStrokeWidth(AdapterWidth(20));
        canvas.drawArc(new RectF(AdapterWidth(80),AdapterWidth(80), AdapterWidth(1000), AdapterWidth(1000)),170,200,false,exeirclePaint);

    }


     //适配屏幕宽度的算法
    public float AdapterWidth(float number){
        float endnumber;
        float scale = 1080/number;
        endnumber = getWidth()/scale;
        return endnumber;
    }


    //屏幕的高
    public float AdapterHeight(float number){
        float endNumber;
        float scale = 860/number;
        endNumber =mHeight/scale;
        return endNumber;
    }

    //文字高度的测量
    public float TextHeight(Paint mPaint){
        Paint.FontMetrics fontMetrics= mPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }



}
