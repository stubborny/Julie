package com.bjtu.julie.Model;

/**
 * Created by carrey on 2018/4/24.
 */

public class User {
    private String username;
    private String password;
    private int id;
    private String userpicUrl;
    private String nickname;
    private String sex;
    private String location;
    private String describe;
    private int isAuthentication;//1已认证0未认证
    private int isLegal;//1合法0非法

    public int getIsLegal() {
        return isLegal;
    }

    public void setIsLegal(int isLegal) {
        this.isLegal = isLegal;
    }

    public int getIsAuthentication() {
        return isAuthentication;
    }

    public void setIsAuthentication(int isAuthentication) {
        this.isAuthentication = isAuthentication;
    }

    public User(String username, String password, int id, String userpicUrl, String nickname, String sex, String location, String describe,int isAuthentication,int isLegal) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.userpicUrl = userpicUrl;
        this.nickname = nickname;
        this.sex = sex;
        this.location = location;
        this.describe = describe;
        this.isAuthentication=isAuthentication;
        this.isLegal=isLegal;
    }
//
//    public User(String username, String password, int id, String userpicUrl, String nickname) {
//        this.username = username;
//        this.password = password;
//        this.id = id;
//        this.userpicUrl = userpicUrl;
//        this.nickname = nickname;
//    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
