package com.mobiusp.server.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class SqlSessionUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SqlSessionUtil.class);
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(in);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            logger.warn(" in Mybatis SqlSessionUtil {}",e.getMessage());
        }
        return sqlSession;
    }
}
