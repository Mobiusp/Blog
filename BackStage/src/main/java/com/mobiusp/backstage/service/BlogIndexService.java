package com.mobiusp.backstage.service;

import com.mobiusp.backstage.controller.Controller;
import com.mobiusp.backstage.mappers.BlogMapper;
import com.mobiusp.backstage.pojo.Blog;
import com.mobiusp.backstage.pojo.BlogIndex;
import com.mobiusp.backstage.pojo.Page;
import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogIndexService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BlogIndexService.class);
    private static int deleteTime = 0;
    private static int updateTime = 0;

    public Page<BlogIndex>  getBlogIndex(Integer index) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Integer tot = mapper.countBlog();
        int start = (index - 1) * 12;
        if (start >= tot) {
            return null;
        }
        List<Blog> blog = mapper.getBlog(start);
        List<BlogIndex> result = new ArrayList<>();
        for (Blog i : blog) {
            result.add(new BlogIndex(i));
        }
        return new Page<BlogIndex>(index, tot,result);
    }

    public boolean updateBlog(Map<String, Object> mp) {
        if (updateTime >= 1) {
            return false;
        }
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        String id = (String) mp.get("id");
        String title = (String) mp.get("title");
        String content = (String) mp.get("content");
        String summary = (String) mp.get("summary");
        if (Objects.equals(id, "") || Objects.equals(title, "") || Objects.equals(content, "") || Objects.equals(summary, "")) {
            return  false;
        }
        int i;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        int blog = mapper.updateBlog(i, title, summary);
        int content1 = mapper.updateContent(i, content);
        if (blog == 1 && content1 == 1) {
            sqlSession.commit();
            updateTime ++;
            return true;
        }else return false;
    }

    public boolean deleteBlog(int id) {
        if (deleteTime >= 1) {
            return false;
        }
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        int b = mapper.deleteBlog(id);
        int c = mapper.deleteContent(id);
        if (b == 1 && c == 1) {
            sqlSession.commit();
            DailyViews.delete(id);
            deleteTime ++;
            return true;
        }else return false;
    }

    public String getContent(int id) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        return mapper.getContent(id);
    }

    @Scheduled(cron="0 57 23 * * *")
    public void checkOnce() {
        deleteTime = 0;
        updateTime = 0;
    }
}
