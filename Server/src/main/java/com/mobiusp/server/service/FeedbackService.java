package com.mobiusp.server.service;

import com.mobiusp.server.mappers.FeedbackMapper;
import com.mobiusp.server.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class FeedbackService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    public boolean newFeedback (Map<String, Object> mp) {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        String uname = (String) mp.get("uname");
        String email = (String) mp.get("email");
        String content = (String) mp.get("content");
        String picOne = (String) mp.get("picOne");
        String picTwo = (String) mp.get("picTwo");
        String picThree = (String) mp.get("picThree");
        if (Objects.equals(uname, "") || Objects.equals(email, "") ||Objects.equals(content, "")) return false;
        int i = mapper.newFeedback(uname, email, content, (Objects.equals(picOne, "") ? null : picOne), (Objects.equals(picTwo, "") ? null : picTwo), (Objects.equals(picThree, "") ? null : picThree));
        if (i == 1) {
            sqlSession.commit();
            return true;
        }
        return false;
    }
}
