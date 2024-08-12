package com.fastcampus.toyproject2.review.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@MapperScan
public class ReviewDaoMysql implements ReviewDao {

    private final SqlSession sqlSession;

    private String namespace = "ReviewMapper.";



}
