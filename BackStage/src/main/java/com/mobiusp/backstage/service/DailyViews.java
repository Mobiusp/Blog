package com.mobiusp.backstage.service;

import com.mobiusp.backstage.mappers.BlogMapper;
import com.mobiusp.backstage.mappers.StatisticMapper;
import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DailyViews {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DailyViews.class);
    private static final Map<Integer, Integer> dayViews = new HashMap<>();

    public DailyViews () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Integer> blogId = mapper.getBlogId();
        for (int i : blogId) {
            dayViews.put (i, 0);
        }
    }

    public static void up (int id) {
        dayViews.replace(id, dayViews.get(id) + 1);
    }

    public static void publish (int id) {
        dayViews.putIfAbsent(id, 0);
    }

    public static void delete (int id) {
        dayViews.remove(id);
    }

    @Scheduled(cron="0 57 23 * * *")
    public void update() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        StatisticMapper mapper = sqlSession.getMapper(StatisticMapper.class);
        dayViews.forEach((i, j) -> {
            mapper.updateSum(i, j);
            mapper.newDay(i, j);
            dayViews.replace(i, 0);
        });
        sqlSession.commit();
    }
}
