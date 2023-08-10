package com.mobiusp.server.service;

import com.mobiusp.server.mappers.BlogCommentMapper;
import com.mobiusp.server.mappers.BlogContentMapper;
import com.mobiusp.server.mappers.BlogMapper;
import com.mobiusp.server.pojo.*;
import com.mobiusp.server.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BlogService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BlogService.class);
    private final RedissonClient redisson;

    @Autowired
    public BlogService(RedissonClient redissonClient) {
        redisson = redissonClient;
        redisson.getBucket("BlogContent").set("null");
        redisson.getBucket("BlogContent0").set("null");
    }
    public BlogContent getBlogContent(Integer blogid){
        String s = (String) redisson.getBucket("BlogContent" + blogid).get();
        if (s != null) return new BlogContent(blogid, s);
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogContentMapper mapper = sqlSession.getMapper(BlogContentMapper.class);
        BlogContent t = mapper.getBlogContent(blogid);
        redisson.getBucket("BlogContent" + blogid).set(t.getContent(), 1, TimeUnit.DAYS);
        return t;
    }

    public String getBlogTitle(Integer blogid) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogContentMapper mapper = sqlSession.getMapper(BlogContentMapper.class);
        sqlSession.close();
        return mapper.getBlogTitle(blogid);
    }
    public List<Comment> getComment(Integer blogid){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogCommentMapper mapper = sqlSession.getMapper(BlogCommentMapper.class);
        List<BlogComment> comments = mapper.getComment(blogid);
        List<BlogTempComment> commentList = new ArrayList<>();
        Map<Integer, BlogTempComment> te = new HashMap<>();
        for(BlogComment it:comments){
            commentList.add(new BlogTempComment(it));
            te.put(it.getId(), new BlogTempComment(it));
        }
        Map<Integer,Comment> mp = new TreeMap<>();
        for (BlogTempComment it: commentList) {
            if(it.getReplyid() == null){
                mp.put(it.getId(),new Comment(it, new ArrayList<>()));
            }
        }
        for (BlogTempComment it: commentList) {
            if(it.getReplyid() != null){
                BlogTempComment tem = it;
                while (te.get(tem.getReplyid()).getReplyid() != null) {
                    tem = te.get(tem.getReplyid());
                }
                mp.get(tem.getReplyid()).getChild().add(it);
            }
        }
        ArrayList<Comment> result = new ArrayList<>(mp.values());
        for(Comment it:result){
            it.getChild().sort(Comparator.comparing(BlogTempComment::getId));
        }
        return result;
    }
    public boolean newParentComment(Map<String ,Object> mp){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogCommentMapper mapper = sqlSession.getMapper(BlogCommentMapper.class);
        int result = mapper.newParentComment(Integer.valueOf((String)mp.get("blogid")),(String) mp.get("uname"),(String)mp.get("email"),(String)mp.get("content"));
        if(result == 1){
            sqlSession.commit();
            logger.info("Successfully added a parent comment");
            return true;
        }else{
            logger.warn("Added a parent comment with an error");
            return false;
        }
    }

    public boolean newChildComment(Map<String, Object> mp) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogCommentMapper mapper = sqlSession.getMapper(BlogCommentMapper.class);
        String temp = (String)mp.get("reply");
        String[] split = temp.split("-");
        int result = mapper.newChildComment(Integer.valueOf((String)mp.get("blogid")),(String)mp.get("uname"),split[1],Integer.valueOf(split[0]),(String)mp.get("content"),(String)mp.get("email"));
        if (result == 1) {
            sqlSession.commit();
            logger.info("Successfully added a child comment");
            return true;
        }else{
            logger.warn("Added a child comment with an error");
            return false;
        }
    }

    public Page<Blog> getBlogPage (int ind) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        int tot = mapper.countBlog();
        int start = 15 * (ind - 1);
        if (start > tot && start < 0) {
            return null;
        }
        return new Page<>(ind, tot, mapper.getBlogPage(start));
    }

    public Page<Blog> searchBlog (String key, int ind) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        int tot = mapper.countSearchBlog(key);
        int start = 15 * (ind - 1);
        if (start > tot && start < 0) {
            return null;
        }
        return new Page<>(ind, tot, mapper.getBlogBySearch(key, start));
    }
}
