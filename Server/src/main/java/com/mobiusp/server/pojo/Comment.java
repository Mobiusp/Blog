package com.mobiusp.server.pojo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Comment {
    private BlogTempComment parent;
    private List<BlogTempComment> child;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
                "parent=" + parent +
                ", child=" + child +
                '}';
    }

    public BlogTempComment getParent() {
        return parent;
    }

    public void setParent(BlogTempComment parent) {
        this.parent = parent;
    }

    public List<BlogTempComment> getChild() {
        return child;
    }

    public void setChild(List<BlogTempComment> child) {
        this.child = child;
    }

    public Comment(BlogTempComment parent, List<BlogTempComment> child) {
        this.parent = parent;
        this.child = child;
    }
}
