package com.bjtu.julie;

import android.app.Application;
import org.xutils.x;
/**
 * Application类，初始化xUntils,需要在manifest里面注册
 * Created by carrey on 2018/3/28.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);
    }
}
