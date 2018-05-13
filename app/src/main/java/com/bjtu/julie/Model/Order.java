package com.bjtu.julie.Model;


import java.io.Serializable;

public class Order implements Serializable {
    private String footId;
    private int userId;
    private int receiveId;
    private int isEvaluate;
    public Order(String footId, int userId, int receiveId, String userpicUrl, String username, String state, String content, String address, String reward, String time, String phone,int isEvaluate) {
        this.receiveId = receiveId;
        this.userId = userId;
        this.footId = footId;
        this.userpicUrl = userpicUrl;
        this.nickname = username;
        this.state = state;
        this.content = content;
        this.address = address;
        this.reward = reward;
        this.time = time;
        this.phone = phone;
        this.isEvaluate=isEvaluate;
    }

    private String userpicUrl;
    private String nickname;
    private String state;
    private String content;
    private String address;
    private String reward;
    private String time;

    private int payOnline;
    private String addNeed;

    public int getPayOnline() {
        return payOnline;
    }

    public void setPayOnline(int payOnline) {
        this.payOnline = payOnline;
    }

    public String getAddNeed() {
        return addNeed;
    }

    public void setAddNeed(String addNeed) {
        this.addNeed = addNeed;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

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

    public int getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(int isEvaluate) {
        this.isEvaluate = isEvaluate;
    }
}
