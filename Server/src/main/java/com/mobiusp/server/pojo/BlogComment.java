package com.mobiusp.server.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Arrays;

public class BlogComment {
    private Integer id;
    private Integer blogid;
    private String uname;
    private String reply;
    private Integer replyid;
    private String content;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;

    @Override
    public String toString() {
        return "BlogComment{" +
                "id=" + id +
                ", blogid=" + blogid +
                ", uname='" + uname + '\'' +
                ", reply='" + reply + '\'' +
                ", replyid=" + replyid +
                ", content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", time=" + time +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogid() {
        return blogid;
    }

    public void setBlogid(Integer blogid) {
        this.blogid = blogid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BlogComment(Integer id, Integer blogid, String uname, String reply, Integer replyid, String content, String email, Timestamp time) {
        this.id = id;
        this.blogid = blogid;
        this.uname = uname;
        this.reply = reply;
        this.replyid = replyid;
        this.content = content;
        this.email = email;
        this.time = time;
    }
}
