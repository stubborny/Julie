package com.bjtu.julie;

import android.app.Application;
import org.xutils.x;
/**
 * Application类，初始化xUntils,需要在manifest里面注册
 * Created by carrey on 2018/3/28.
 */

public class MyApplication extends Application {
    private int status;//监测用户登录状态，1是登录，0是未登录
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);
        setStatus(0);
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return status;
    }
}
