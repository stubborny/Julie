package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/4/15.
 */

public class Comment {
    private String commName; //用户名
    private  int commImageId; //用户头像
    public Comment(String commName,int commImageId){
        this.commName=commName;
        this.commImageId=commImageId;
    }
    public String getCommName(){
        return commName;
    }
    public int getCommImageId() {
        return commImageId;
    }

}
