package com.mobiusp.server.mappers;

import com.mobiusp.server.pojo.BlogContent;

public interface BlogContentMapper {
    BlogContent getBlogContent(Integer id);
    String getBlogTitle(Integer id);
}
