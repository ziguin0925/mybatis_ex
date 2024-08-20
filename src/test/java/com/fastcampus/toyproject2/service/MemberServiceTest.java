package com.fastcampus.toyproject2.service;

import com.fastcampus.toyproject2.member.dto.MemberDto;
import com.fastcampus.toyproject2.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/* MemberService Test *
   (**)는 테스트 예정

 * read
 * - 회원 id로 회원 정보 가져오기
 * - 존재하지 않는 회원 id로 회원 정보 가져오기
 *
 * modify
 * - 회원 id로 회원 정보 수정하기
 * - 존재하지 않는 회원 id로 회원 정보 수정하기
 *
 * create
 * - 새로운 회원 추가하기 - 성공
 * - ** 아이디 중복인 회원 추가하기 --> 추가되는거 어디서 막는거지?
 * - 아이디 중복 체크
 * - 이메일 중복 체크
 *
 * remove
 * - 회원 id로 회원 정보 삭제하기
 *
 * 경계성 테스트
 * - ** 잘못된 입력값
 * - ** 데이터베이스 연결 실패
 */

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    /* read
     * - 회원 id로 회원 정보 가져오기
     * - 존재하지 않는 회원 id로 회원 정보 가져오기
     */
    @Test
    public void read() {
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
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

        /* 1. 회원 id로 회원 정보 가져오기 */
        // 1명 회원 가입
        memberService.createMemberByAllInfo(memberDto);
        assertTrue(memberService.countMember()==1);
        // id로 가입한 회원 정보 가져오기
        assertTrue(memberService.readMemberByMemberId("asdf1234").getId().equals(memberDto.getId()));


        /* 2. 존재하지 않는 회원 id로 회원 정보 가져오기 */
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);

        // 1명 회원 가입
        memberService.createMemberByAllInfo(memberDto);
        assertTrue(memberService.countMember()==1);

        // 존재하지 않는 id로 가입한 회원 정보 가져오기

        assertTrue(memberService.readMemberByMemberId("qwert1234")==null);
    }

     /* modify
      * 1. 회원 id로 회원 정보 수정하기
      * 2. 존재하지 않는 회원 id로 회원 정보 수정하기
      */
    @Test
    public void modify() {
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
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

        /* 1. 회원 id로 회원 정보 수정하기 */
        // 1명 회원 가입
        memberService.createMemberByAllInfo(memberDto);
        assertTrue(memberService.countMember()==1);
        // id로 가입한 회원 정보 가져와서 확인
        MemberDto result = memberService.readMemberByMemberId("asdf1234");
        assertTrue(result.getId().equals(memberDto.getId()));
        // 휴대폰 번호 변경하기
        result.setPhone_number("01000000000");
        // 회원 정보 업데이트 한 후, 변경 확인
        assertTrue(memberService.modifyMemberInfoByMemberId(result)==1);
        assertTrue(memberService.readMemberByMemberId("asdf1234").getPhone_number().equals("01000000000"));

        /* 2. 존재하지 않는 회원 id로 회원 정보 수정하기 */
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
        // 1명 회원 가입
        memberService.createMemberByAllInfo(memberDto);
        assertTrue(memberService.countMember()==1);
        // id로 가입한 회원 정보 가져와서 확인
        MemberDto result2 = memberService.readMemberByMemberId("asdf1234");
        assertTrue(result2.getId().equals(memberDto.getId()));
        // 휴대폰 번호 변경하기
        result2.setId("qwert1234");
        result2.setPhone_number("01000000000");
        // 회원 정보 업데이트 한 후, 변경 확인
        assertTrue(memberService.modifyMemberInfoByMemberId(result2)==0);

    }

    /* create
     * 1. 새로운 회원 추가하기 - 성공
     * 2. 아이디 중복인 회원 추가하기 - 실패
     * 3. 아이디 중복 체크
     * 4. 이메일 중복 체크
     */
    @Test
    public void create() {
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
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

        /* 1. 새로운 회원 추가하기 - 성공 */
        // 새로운 회원 추가
        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
        assertTrue(memberService.countMember()==1);
        // 추가한 회원 id로 회원정보 가져와서 id 비교 성공
        assertTrue(memberService.readMemberByMemberId("asdf1234").getId().equals(memberDto.getId()));

        /* 2. 아이디 중복인 회원 추가하기 - 실패 */
//        // 전체 회원 정보 삭제 후, count = 0
//        memberService.removeAll();
//        assertTrue(memberService.countMember()==0);
//        // 새로운 회원 추가
//        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
//        assertTrue(memberService.countMember()==1);
//        // id 중복으로 회원 추가
//        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
//        assertTrue(memberService.countMember()==1);

        /* 3. 아이디 중복 체크 */
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
        // 새로운 회원 추가
        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
        assertTrue(memberService.countMember()==1);
        // 회원 아이디 중복 체크 성공
        assertTrue(memberService.checkDuplID(memberDto.getId())==1);

        /* 4. email 중복 체크 */
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
        // 새로운 회원 추가
        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
        assertTrue(memberService.countMember()==1);
        // 회원 email 중복 체크 성공
        System.out.println("Email = " + memberService.readMemberByMemberId("asdf1234").getEmail());
        assertTrue(memberService.checkDuplEmail("asdf@naver.com")==1);
    }

     /* remove
      * 1. 회원 id로 회원 정보 삭제하기
      */
    @Test
    public void remove() {
        // 전체 회원 정보 삭제 후, count = 0
        memberService.removeAll();
        assertTrue(memberService.countMember()==0);
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

        /* 1. 회원 id로 회원 정보 삭제하기 */
        // 새로운 회원 추가
        assertTrue(memberService.createMemberByAllInfo(memberDto)==1);
        assertTrue(memberService.countMember()==1);
        // 추가한 회원 id로 회원정보 가져와서 id 비교 성공
        assertTrue(memberService.readMemberByMemberId("asdf1234").getId().equals(memberDto.getId()));
        // 추가한 회원 id로 삭제
        assertTrue(memberService.removeMemberByID(memberDto.getId())==1);
        assertTrue(memberService.countMember()==0);
    }

}