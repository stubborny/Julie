package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/4/27.
 */

public class Grade {
    private String lable;
    private int userId;
    private String userpicUrl;
    private String name;
    private int floor;
    private String gradeLevel;
    private String time;
    public Grade( String name,int userId) {
        super();
        this.userId = userId;
        this.name = name;
    }
   /* public Grade(String lable, int userId, String userpicUrl, String name, int floor, String gradeLevel,
                   String time) {
        super();
        this.lable = lable;
        this.userId = userId;
        this.userpicUrl = userpicUrl;
        this.name = name;
        this.floor = floor;
        this.gradeLevel = gradeLevel;
        this.time = time;
    }*/

    public String getLable() {
        return lable;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserpicUrl() {
        return userpicUrl;
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public String getTime() {
        return time;
    }

}
