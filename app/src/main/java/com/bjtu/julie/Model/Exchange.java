package com.bjtu.julie.Model;

import java.io.Serializable;

/**
 * Created by dadada on 2018/3/31.
 */
//信息实体类
public class Exchange implements Serializable {
    private String name; //用户名
    private String userpicUrl; //用户头像
    private String phone; // 订单联系电话
    private String content; // 订单内容
    private String wechat; // weixin
    private String time;
    private String messId;
    private String commentNum;
    private String likeNum;
    private int userId;



    public Exchange(String messId,int userId,String name,String userpicUrl,String phone,String content,String wechat,String time,String commentNum,String likeNum){
        this.name=name;
        this.userId = userId;
        this.userpicUrl=userpicUrl;
        this.phone=phone;
        this.content=content;
        this.wechat=wechat;
        this.time=time;
        this.messId=messId;
        this.commentNum=commentNum;
        this.likeNum=likeNum;

    }



    public Exchange(String name,String userpicUrl,String content,String time){
        this.name=name;
        this.userpicUrl=userpicUrl;
        this.content=content;
        this.time=time;
    }
    public String getMessId() {
        return messId;
    }

    public void setMessId(String messId) {
        this.messId = messId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
        this.userpicUrl = userpicUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time=time;
    }

    public String getcommentNum(){
        return commentNum;
    }

    public void setcommentNum(String commentNum){
        this.commentNum=commentNum;
    }

    public String getLikeNum(){
        return likeNum;
    }

    public void setLikeNum(String likeNum){
        this.likeNum=likeNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
