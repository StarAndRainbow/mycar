package com.ityingli.www.car1.Activitypake;

import android.app.Application;

/**
 * Created by Administrator on 2017/4/20.
 */
public class MyApplication extends Application {

    private static boolean t1IsStart;
    private static  int myposition;

    @Override
    public void onCreate() {
        super.onCreate();
        t1IsStart = false;
    }

    public static  boolean gett1IsStart(){
        return t1IsStart;
    }

    public static  void sett1IsStart(boolean mt1IsStart){
       t1IsStart  = mt1IsStart;
    }

    public static int getMyposition() {
        return myposition;
    }

    public static void setMyposition(int myposition) {
        MyApplication.myposition = myposition;
    }
}
