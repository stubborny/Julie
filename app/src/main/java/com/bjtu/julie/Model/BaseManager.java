package com.bjtu.julie.Model;

/**
 * Created by carrey on 2018/4/24.
 */

public class BaseManager {

    //静态用户定义
    protected static User mUser;

    //根据返回值boolean判断用户是否已经登陆
    public boolean isLogined() {
        return mUser != null;
    }

    //返回当前用户
    public User getUser() {
        return mUser;
    }

    //定义当前用户
    public void setUser(User user) {
        mUser = user;
    }

}
