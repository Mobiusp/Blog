package com.mobiusp.server.service;

import com.mobiusp.server.mappers.BlogMapper;
import com.mobiusp.server.pojo.Blog;
import com.mobiusp.server.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    public List<Blog> getBlogs(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> result = mapper.getBlogs();
        result.sort((i,j) -> {
            return j.getId().compareTo(i.getId());
        });
        return result;
    }
}
