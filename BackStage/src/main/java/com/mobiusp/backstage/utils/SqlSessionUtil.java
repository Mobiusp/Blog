package com.mobiusp.backstage.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SqlSessionUtil.class);
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(in);
            sqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            logger.error(" in Mybatis SqlSessionUtil {}",e.getMessage());
        }
        return sqlSession;
    }

}
