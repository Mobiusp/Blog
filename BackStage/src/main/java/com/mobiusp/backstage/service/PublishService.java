package com.mobiusp.backstage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mobiusp.backstage.mappers.PublishMapper;
import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
public class PublishService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PublishService.class);
    public boolean publishBlog(Map<String, Object> mp){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        PublishMapper mapper = sqlSession.getMapper(PublishMapper.class);
        String c = (String) mp.get("content");
        String t = (String) mp.get("title");
        String s = (String) mp.get("summary");
        if (Objects.equals(c, "") || Objects.equals(t, "") || Objects.equals(s, "")) {
            logger.error("Can not add empty content.");
            return false;
        }
        int content = mapper.newBlogContent(c);
        int blog = mapper.newBlog(t, s);
        if (content == 1 && blog == 1) {
            sqlSession.commit();
            DailyViews.publish(mapper.getNewId());
            return  true;
        }
        logger.error("Add new blog failed.");
        return false;
    }
}
