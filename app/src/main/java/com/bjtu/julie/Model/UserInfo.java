package com.bjtu.julie.Model;

/**
 * Created by Dell on 2018/4/23.
 */

public class UserInfo {
    private String username;;
    private String sex;
    private String location;
    private String describe;
    private String nickname;
    private String picurl;

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public UserInfo(String username, String sex, String location, String describe, String nickname) {
        this.username=username;
        this.sex = sex;
        this.location = location;
        this.describe = describe;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}