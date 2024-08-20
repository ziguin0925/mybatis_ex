package com.fastcampus.toyproject2.member.service;

import com.fastcampus.toyproject2.member.dao.MemberDao;
import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberService {
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

    /* 중복 id 카운트 */
    public int checkDuplID(String id) {
        return memberDao.countID(id);
    }

    /* 중복 Email 카운트 */
    public int checkDuplEmail(String email) {
        return memberDao.countEmail(email);
    }

    /* id로 회원 정보 가져오기 */
    public MemberDto readMemberByMemberId(String id) {
        return memberDao.selectMemberByMemberId(id);
    }

    /* 회원 id, password로 회원 정보 가져오기 */
    public MemberDto readMemberByIdPassword(String id, String password) {
        return memberDao.selectMemberByMemberIdPassword(id, password);
    }

    /* 회원 가입 */
    public int createMemberByAllInfo(MemberDto memberDto) {
        return memberDao.insertMemberAllInfo(memberDto);
    }

    /* id로 회원 정보 수정 */
    public int modifyMemberInfoByMemberId(MemberDto memberDto) {
        return memberDao.updateMemberInfoByMemberId(memberDto);
    }

    /* id로 관리자 여부 수정 */
    public int modifyIsAdminByMemberId(String id, String is_admin) {
        return memberDao.updateIsAdminByMemberId(id, is_admin);
    }

    /* id로 회원 정보 삭제하기 */
    public int removeMemberByID(String id){
        return memberDao.deleteByMemberID(id);
    }

    /* 모든 회원 정보 가져오기 - 관리자 모드 */
    // List<MemberDto> selectAllMemberByMemberId(String is_admin);

    /* 관리자 모드 수정 */
    // int updateIsAdminByMemberId(String id, String is_admin);
}


