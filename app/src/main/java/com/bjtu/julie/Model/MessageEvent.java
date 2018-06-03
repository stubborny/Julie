package com.bjtu.julie.Model;

/**
 * Created by carrey on 2018/4/23.
 */

public class MessageEvent {
    private String message;
    private Discount discount;
    private String state;
    private int position;
    private int receivrId;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, int position) {
        this.message = message;
        this.position = position;
    }


    public int getReceivrId() {
        return receivrId;
    }

    public void setReceivrId(int receivrId) {
        this.receivrId = receivrId;
    }

    public MessageEvent(Discount discount) {
        this.discount = discount;
    }

    public String getMessage() {
        return message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
