package com.mobiusp.server.mappers;

import com.mobiusp.server.pojo.Blog;

import java.util.List;

public interface BlogMapper {
     List<Blog> getBlogs();
     int countBlog();
     List<Blog> getBlogPage(int ind);
     List<Blog> getBlogBySearch (String key, int ind);
     int countSearchBlog (String key);
}
