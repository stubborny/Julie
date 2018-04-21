package com.bjtu.julie.Model;


import java.io.Serializable;

public class Order implements Serializable {
    private String footId;

    public Order(String footId, String userpicUrl, String username, String state, String content, String address, String reward, String time,String phone) {
        this.footId = footId;
        this.userpicUrl = userpicUrl;
        this.nickname = username;
        this.state = state;
        this.content = content;
        this.address = address;
        this.reward = reward;
        this.time = time;
        this.phone=phone;
    }

    private String userpicUrl;
    private String nickname;
    private String state;
    private String content;
    private String address;
    private String reward;
    private String time;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;


    public String getFootId() {
        return footId;
    }

    public void setFootId(String footId) {
        this.footId = footId;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
