package com.mobiusp.server.pojo;

public class BlogContent {
    private Integer id;
    private String content;

    @Override
    public String toString() {
        return "BlogContent{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogContent(Integer id, String content) {
        this.id = id;
        this.content = content;
    }
}
