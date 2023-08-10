package com.mobiusp.backstage.service;

import com.mobiusp.backstage.controller.Controller;
import com.mobiusp.backstage.mappers.CommentMapper;
import com.mobiusp.backstage.pojo.BlogComment;
import com.mobiusp.backstage.pojo.Comment;
import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CommentService.class);
    private static int deleteTime = 0;
    public List<Comment> getComment (int blogid) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        List<BlogComment> comment = mapper.getComment(blogid);
        Map<Integer, Comment> mp = new TreeMap<>();
        Map<Integer, BlogComment> te = new HashMap<>();
        for (BlogComment i : comment) {
            te.put(i.getId(), i);
            if (i.getReplyid() == null) {
                mp.put(i.getId(), new Comment(i, new ArrayList<>()));
            }
        }
        for (BlogComment i : comment) {
            if (i.getReplyid() != null) {
                BlogComment tem = i;
                while (te.get(tem.getReplyid()).getReplyid() != null) {
                    tem = te.get(tem.getReplyid());
                }
                mp.get(tem.getReplyid()).getChildComment().add(i);
            }
        }
        ArrayList<Comment> comments = new ArrayList<>(mp.values());
        for (Comment i : comments) {
            i.getChildComment().sort(Comparator.comparing(BlogComment::getId));
        }
        return comments;
    }

    public boolean deleteComment (int id) {
        if (deleteTime >= 1) {
            return false;
        }
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        int i = mapper.deleteComment(id);
        if (i == 1) {
            sqlSession.commit();
            deleteTime ++;
            return true;
        }
        return false;
    }

    public boolean deleteComment (int []id) {
        if (deleteTime >= 1) {
            return false;
        }
        boolean flag = true;
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        for (int i : id) {
            int te = mapper.deleteComment(i);
            if (te != 1) flag = false;
        }
        if (flag) {
            sqlSession.commit();
            deleteTime ++;
            return true;
        }
        return false;
    }

    @Scheduled(cron="0 0 0 * * *")
    public void checkOnce() {
        deleteTime = 0;
    }
}
