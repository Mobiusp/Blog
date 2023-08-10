package com.mobiusp.server.pojo;

import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.util.Date;

public class BlogTempComment {
    private Integer id;
    private String uname;
    private String reply;
    private Integer replyid;
    private String content;
    private String time;
    private String email;
    public  BlogTempComment(BlogComment blogComment){
        this.id = blogComment.getId();
        this.uname = blogComment.getUname();
        this.reply = blogComment.getReply();
        this.replyid = blogComment.getReplyid();
        this.content = blogComment.getContent();
        this.email = DigestUtils.md5DigestAsHex(blogComment.getEmail().getBytes());
        Long now = Date.from(new Timestamp(System.currentTimeMillis()).toInstant()).getTime()  - Date.from(blogComment.getTime().toInstant()).getTime();
        if(now >= 31536000000L){
            Long year = (Long)now/31536000000L;
            time = year.toString() + "年前";
        }else if (now >= 2592000000L){
            Long month = (Long)now/2592000000L;
            time = month.toString() + "月前";
        }else if (now >= 86400000L){
            Long day = (Long)now/86400000L;
            time = day.toString() + "天前";
        }else if (now >= 3600000L){
            Long hour = (Long)now/3600000L;
            time = hour.toString() + "小时前";
        }else if (now >= 60000L){
            Long minute = (Long)now/36000L;
            time = minute.toString() + "分钟前";
        }else {
            time = "1分钟前";
        }
    }

    public BlogTempComment(Integer id, String uname, String reply, Integer replyid, String content, String time, String email) {
        this.id = id;
        this.uname = uname;
        this.reply = reply;
        this.replyid = replyid;
        this.content = content;
        this.time = time;
        this.email = email;
    }

    @Override
    public String toString() {
        return "BlogTempComment{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", reply='" + reply + '\'' +
                ", replyid=" + replyid +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getUname() {
        return uname;
    }

    public String getReply() {
        return reply;
    }

    public Integer getReplyid() {
        return replyid;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getEmail() {
        return email;
    }
    public BlogTempComment(BlogTempComment blogTempComment) {
        id = blogTempComment.getId();
        uname = blogTempComment.getUname();
        reply = blogTempComment.getReply();
        replyid = blogTempComment.getReplyid();
        content = blogTempComment.getContent();
        time = blogTempComment.getTime();
        email = blogTempComment.getEmail();
    }
}
