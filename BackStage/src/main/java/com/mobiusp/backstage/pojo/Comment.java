package com.mobiusp.backstage.pojo;

import java.util.List;

public class Comment {

    private BlogComment parentComment;
    private List<BlogComment> childComment;

    public Comment(){}

    @Override
    public String toString() {
        return "Comment{" +
                "parentComment=" + parentComment +
                ", childComment=" + childComment +
                '}';
    }

    public BlogComment getParentComment() {
        return parentComment;
    }

    public void setParentComment(BlogComment parentComment) {
        this.parentComment = parentComment;
    }

    public List<BlogComment> getChildComment() {
        return childComment;
    }

    public void setChildComment(List<BlogComment> childComment) {
        this.childComment = childComment;
    }

    public Comment(BlogComment parentComment, List<BlogComment> childComment) {
        this.parentComment = parentComment;
        this.childComment = childComment;
    }
}
