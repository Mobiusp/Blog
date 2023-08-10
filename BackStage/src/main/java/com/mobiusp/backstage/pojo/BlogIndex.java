package com.mobiusp.backstage.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;

public class BlogIndex {
    private Integer id;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp time;

    public BlogIndex(Blog e) {
        id = e.getId();
        title = e.getTitle();
        time = e.getTime();
    }

    @Override
    public String toString() {
        return "BlogIndex{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BlogIndex(Integer id, String title, Timestamp time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }
}
