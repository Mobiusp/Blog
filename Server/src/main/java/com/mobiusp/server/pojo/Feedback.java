package com.mobiusp.server.pojo;

import java.sql.Timestamp;

public class Feedback {
    private int id;
    private String uname;
    private String email;
    private String content;
    private Timestamp time;

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Feedback(int id, String uname, String email, String content, Timestamp time) {
        this.id = id;
        this.uname = uname;
        this.email = email;
        this.content = content;
        this.time = time;
    }
}
