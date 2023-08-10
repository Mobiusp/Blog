package com.mobiusp.backstage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class FeedBack {
    private int id;
    private String uname;
    private String email;
    private String content;
    private String picOne;
    private String picTwo;
    private String picThree;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp subTime;
    private boolean isHandled;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp handledTime;

    @Override
    public String toString() {
        return "FeedBack{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", picOne='" + picOne + '\'' +
                ", picTwo='" + picTwo + '\'' +
                ", picThree='" + picThree + '\'' +
                ", subTime=" + subTime +
                ", isHandled=" + isHandled +
                ", handledTime=" + handledTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicOne() {
        return picOne;
    }

    public void setPicOne(String picOne) {
        this.picOne = picOne;
    }

    public String getPicTwo() {
        return picTwo;
    }

    public void setPicTwo(String picTwo) {
        this.picTwo = picTwo;
    }

    public String getPicThree() {
        return picThree;
    }

    public void setPicThree(String picThree) {
        this.picThree = picThree;
    }

    public Timestamp getSubTime() {
        return subTime;
    }

    public void setSubTime(Timestamp subTime) {
        this.subTime = subTime;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setHandled(boolean handled) {
        isHandled = handled;
    }

    public Timestamp getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Timestamp handledTime) {
        this.handledTime = handledTime;
    }

    public FeedBack(int id, String uname, String email, String content, String picOne, String picTwo, String picThree, Timestamp subTime, boolean isHandled, Timestamp handledTime) {
        this.id = id;
        this.uname = uname;
        this.email = email;
        this.content = content;
        this.picOne = picOne;
        this.picTwo = picTwo;
        this.picThree = picThree;
        this.subTime = subTime;
        this.isHandled = isHandled;
        this.handledTime = handledTime;
    }
}
