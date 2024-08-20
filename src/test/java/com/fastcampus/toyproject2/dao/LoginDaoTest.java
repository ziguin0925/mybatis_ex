package com.fastcampus.toyproject2.dao;

import com.fastcampus.toyproject2.member.dao.LoginDao;
import com.fastcampus.toyproject2.member.dao.MemberDao;
import com.fastcampus.toyproject2.member.dto.LoginDto;
import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LoginDaoTest {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private LoginDao loginDao;

    /* 로그인 테스트 *
     * 1. 로그인 성공
     * 2. 로그인 실패 - id가 회원 정보에 없을 경우
     * 3. 로그인 실패 - pwd가 틀릴 경우
     */
    @Test
    void selectMemberByMemberIdPassword() {
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

        // MemberDto 생성
        MemberDto memberDto = new MemberDto(
                "40001",
                "asdf1234",
                "asdf1234!",
                "김진철",
                "2000-08-22",
                "M",
                "01012345678",
                "asdf@naver.com",
                "",
                "N");

        /* 1. 로그인 성공 */
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // id 조회 성공
        assertTrue(loginDao.selectMemberByMemberId(memberDto.getId()).getId().equals("asdf1234"));
        // 비밀번호 조회 성공
        LoginDto result1 = loginDao.selectMemberByMemberIdPassword("asdf1234", "asdf1234!");
        assertTrue(result1.getId().equals("asdf1234") && result1.getPassword().equals("asdf1234!"));

        /* 2. 로그인 실패 - id가 회원 정보에 없을 경우 */
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // id 조회 실패
        assertNull(loginDao.selectMemberByMemberId("qwert1234"));
        // 없는 id로 조회시, 가져오는 정보 없음
        LoginDto result2 = loginDao.selectMemberByMemberIdPassword("qwert1234", "asdf1234!");
        assertNull(result2);


        /* 로그인 실패 - 비밀번호가 틀릴 경우 */
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // id 조회 성공
        assertTrue(loginDao.selectMemberByMemberId(memberDto.getId()).getId().equals("asdf1234"));
        // 비밀번호가 틀렸을 경우, 가져오는 정보 없음
        LoginDto result3 = loginDao.selectMemberByMemberIdPassword(memberDto.getId(), "asdf123!");
        assertNull(result3);

    }
}