package com.fastcampus.toyproject2.member.service;

import com.fastcampus.toyproject2.member.dao.LoginDao;
import com.fastcampus.toyproject2.member.dao.MemberDao;
import com.fastcampus.toyproject2.member.dto.LoginDto;
import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LoginService {
    @Autowired
    LoginDao loginDao;
    @Autowired
    MemberDao memberDao;

    /* 모든 회원 정보 삭제 */
    public int removeAll(){
        return memberDao.deleteAll();
    }

    /* 모든 회원 정보 카운트 */
    public int countMember(){
        return memberDao.count();
    }

    /* 존재하는 id이면 회원 id, password로 로그인 확인 */
    public LoginDto readMemberByIdPassword(String id, String password) {
        return loginDao.selectMemberByMemberIdPassword(id, password);
    }

    /* 회원 id로 회원 인지 체크 */
    public LoginDto readMemberById(String id) {
        return loginDao.selectMemberByMemberId(id);
    }
}


