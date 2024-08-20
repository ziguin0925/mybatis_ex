package com.fastcampus.toyproject2.member.dao;

import com.fastcampus.toyproject2.member.dto.LoginDto;
import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LoginDao {
    // 회원 id, password로 회원 정보 가져오기
    LoginDto selectMemberByMemberIdPassword(String id, String password);

    // 회원 id로 회원 정보 가져오기
    LoginDto selectMemberByMemberId(String id);
}


