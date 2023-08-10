package com.mobiusp.backstage.mappers;

import com.mobiusp.backstage.pojo.BlogComment;

import java.util.List;

public interface CommentMapper {
    List<BlogComment> getComment(int blogid);
    int deleteComment(int id);
}
