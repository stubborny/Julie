package com.bjtu.julie.Model;

/**
 * Created by carrey on 2018/4/23.
 */

public class MessageEvent {
    private String message;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    private Discount discount;


    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
