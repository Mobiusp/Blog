package com.mobiusp.backstage.mappers;

public interface PublishMapper {

    int newBlogContent(String content) ;
    int newBlog(String title, String summary);
    int getNewId ();
}
