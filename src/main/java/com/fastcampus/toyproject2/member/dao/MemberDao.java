package com.fastcampus.toyproject2.member.dao;

import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MemberDao {
    // 회원 id로 회원 정보 가져오기
    MemberDto selectMemberByMemberId(String id);
    // 회원 id, password로 회원 정보 가져오기 - 로그인 시
    MemberDto selectMemberByMemberIdPassword(String id, String password);
    // 모든 회원 정보 가져오기 - 관리자 모드
    List<MemberDto> selectAllMemberByMemberId(String is_admin);
    // 모든 회원 지우기
    int deleteAll();
    // 모든 회원 갯수 세기
    int count();
    // id 중복 검사 - 탈퇴 회원 제외
    int countID(String id);
    // email 중복 검사 - 탈퇴 회원 제외
    int countEmail(String email);
    // id로 회원 삭제
    int deleteByMemberID(String id);
    // 회원 가입
    int insertMemberAllInfo(MemberDto memberDto);
    // 회원 정보 수정 ( 전체 정보 )
    int updateMemberInfoByMemberId(MemberDto memberDto);
    // 관리자 모드 수정
    int updateIsAdminByMemberId(String id, String is_admin);
}


