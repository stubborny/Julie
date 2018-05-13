package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/4/15.
 */

public class Discount {
    private int id;
    private String name;
    private String money;
    private String usable;
    private String deadline;
    private String limit;

    public Discount(int id,String name, String money, String usable, String deadline, String limit) {
        this.id=id;
        this.name = name;
        this.money = money;
        this.usable = usable;
        this.deadline = deadline;
        this.limit = limit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
