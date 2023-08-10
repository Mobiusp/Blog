package com.mobiusp.backstage.mappers;

import com.mobiusp.backstage.pojo.Blog;

import java.util.List;

public interface BlogMapper {
    List<Blog> getBlog(Integer index);
    Integer countBlog();
    int deleteBlog(int id);
    int deleteContent(int id);
    int updateBlog(int id, String title, String summary);
    int updateContent(int id, String content);
    String getContent(int id);
    List<Integer> getBlogId();
}
