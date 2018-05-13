package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/5/1.
 */

public class Grade {
    private int userId;
    private String userpicUrl;
    private String name;
    private int rate;
    private String time;
    private String content;
    private int evaluate1;
    private int evaluate2;
    private int evaluate3;


    public Grade(int userId, String userpicUrl, String name, int rate, String time, String content, int a, int b, int c) {
        this.userId=userId;
        this.userpicUrl=userpicUrl;
        this.content = content;
        this.name=name;
        this.rate=rate;
        this.time=time;
        this.evaluate1=a;
        this.evaluate2=b;
        this.evaluate3=c;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }

    public int getEvaluate1() {
        return evaluate1;
    }

    public void setEvaluate1(int evaluate1) {
        this.evaluate1 = evaluate1;
    }

    public int getEvaluate2() {
        return evaluate2;
    }

    public void setEvaluate2(int evaluate2) {
        this.evaluate2 = evaluate2;
    }

    public int getEvaluate3() {
        return evaluate3;
    }

    public void setEvaluate3(int evaluate3) {
        this.evaluate3 = evaluate3;
    }
}
