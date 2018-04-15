package com.bjtu.julie.Model;

/**
 * Created by dadada on 2018/4/15.
 */

public class Discount {
    private String money;
    private String discountName;
    public Discount(String money,String discountName){
        this.money=money;
        this.discountName=discountName;
    }
    public String getMoney() {
        return money;
    }
    public String getDiscountName() {
        return discountName;
    }

}
