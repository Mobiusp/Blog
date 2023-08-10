package com.mobiusp.backstage.service;

import com.mobiusp.backstage.mappers.FeedbackMapper;
import com.mobiusp.backstage.pojo.FeedBack;
import com.mobiusp.backstage.pojo.FeedbackUtil;
import com.mobiusp.backstage.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackService {
    public FeedbackUtil getFeedback () {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        List<FeedBack> unhandledFeedback = mapper.getUnhandledFeedback();
        List<FeedBack> handledFeedback = mapper.getHandledFeedback();
        return new FeedbackUtil(unhandledFeedback, handledFeedback);
    }

    public Timestamp changeFeedback (Map<String, Object> mp) {
        int id = (int) mp.get("id");
        boolean isHandled = (boolean) mp.get("isHandled");
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        int i;
        if (isHandled) {
            i = mapper.changeUnhandledFeedback(id);
        }
        else i = mapper.changeHandledFeedback(id);
        if (i == 1) {
            sqlSession.commit();
        }
        Timestamp changeTime = mapper.getChangeTime(id);
        return (changeTime == null ? new Timestamp(System.currentTimeMillis()) : changeTime);
    }
}