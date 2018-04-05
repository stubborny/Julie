package com.bjtu.julie.Model;


public class Order {
    private String title;
    private String money;
    private String address;
    private String time;

    public Order(String title,String money,String address, String time){
        this.title=title;
        this.money=money;
        this.address=address;
        this.time=time;

    }

    public String getTitle() {
        return title;
    }

    public String getMoney() {
        return money;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

}
