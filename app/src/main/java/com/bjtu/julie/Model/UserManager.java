package com.bjtu.julie.Model;

/**
 * Created by carrey on 2018/4/24.
 */

public class UserManager extends BaseManager{

    private static UserManager instance;
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
}
