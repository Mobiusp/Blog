package com.mobiusp.server.mappers;

import com.mobiusp.server.pojo.BlogComment;

import java.util.List;

public interface BlogCommentMapper {
    List<BlogComment> getComment(Integer blogid);
    int newParentComment(Integer blogid,String uname,String email,String content);
    int newChildComment(Integer blogid,String uname,String reply,Integer reply_id,String content,String email);
}
