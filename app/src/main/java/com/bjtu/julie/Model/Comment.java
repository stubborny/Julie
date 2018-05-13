package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/4/15.
 */

public class Comment {
    private String footId;
    private int userId;
    private String userpicUrl;
    private String nickname;
    private int floor;
    private String comment;
    private String time;


    public Comment(String footId, int userId, String userpicUrl, String nickname, int floor, String comment,
                String time) {
        super();
        this.footId = footId;
        this.userId = userId;
        this.userpicUrl = userpicUrl;
        this.nickname = nickname;
        this.floor = floor;
        this.comment = comment;
        this.time = time;
    }

    public String getFootId() {
        return footId;
    }

    public void setFootId(String footId) {
        this.footId = footId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

