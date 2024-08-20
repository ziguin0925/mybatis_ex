package com.fastcampus.toyproject2.dao;

import com.fastcampus.toyproject2.member.dao.MemberDao;
import com.fastcampus.toyproject2.member.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/* MemberDao Test *
  (**)는 테스트 예정

 * deleteAllTest
 * - 모든 회원 정보 지우기 성공
 *
 * countIDTest
 * - 아이디로 카운팅 했을 때 중복 아이디 있으면 1 리턴
 * - 아이디로 카운팅 했을 때 중복 아이디 없으면 0 리턴
 *
 * countEmailTest
 * - 이메일로 카운팅 했을 때 중복 이메일 있으면 1 리턴
 * - 이메일로 카운팅 했을 때 중복 이메일 없으면 0 리턴
 *
 * deleteByMemberIDTest
 * - 아이디가 같은 경우 삭제 성공
 * - 아이디가 회원정보에 없는 경우 삭제 실패
 *
 * selectMemberByMemberIdTest
 * - 아이디를 이용해서 있을 경우, 조회 성공
 * - 아이디를 이용해서 없을 경우, 조회 실패
 * - 회원가입 2개 있을 때 아이디로 조회 성공
 *
 * selectMemberByMemberIdPasswordTest - 로그인
 * - 아이디를 이용해서 있을 경우, 조회 성공
 * - 아이디를 이용해서 없을 경우, 조회 실패
 * - 회원가입 2개 있을 때 아이디로 조회 성공
 *
 * selectAllMemberByMemberId
 * - ** 모든 회원 리스트 가져오기 - 관리자
 * - ** 모든 회원 리스트 가져오기 - 일반회원
 *
 * updateMemberInfoByMemberIdTest
 * - 회원 id로 회원 정보(단일 필드) 업데이트 성공
 * - 회원 id로 회원 정보(다중 필드) 업데이트 성공
 * - ** 존재하지 않는 회원 id로 데이터 업데이트 시도 실패 - 적절한 결과 처리
 * - ** (조건부 업데이트 성공)
 *       --> 로그인 시도 횟수가 3번이면 '잠금' 성공, '잠금' 이후 로그인 성공하면 '활동' 상태 변경 성공
 *       --> 회원정보 변경 시 중복되면 안되는 컬럼 변경 시 실패
 *
 * 예외 처리 테스트 - SQL 쿼리 실행 중 발생하는 예외처리 테스트
 * - ** SQLSyntaxErrorException : SQL 구문 오류
 * - ** DataIntegrityViolationException : 무결성 제약 조건 위반
 * - ** PermissionDeniedException : 사용자 권한 --> 관리자, 회원 필요?
 * - ** 회원 등록 때 1개의 정보가 빠졌을 때, 아이디 조회시 실패 (각 컬럼마다 제외)
 */

@SpringBootTest
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    /* 모든 회원 정보 삭제 테스트  */
    @Test
    void deleteAllTest() {
        // 1. 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count()==0);

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
        // 새로운 회원 1명 insert
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        // 2. 새로운 회원 deleteAll하면 1명 삭제 성공
        assertTrue(memberDao.deleteAll()==1);
        // 모든 데이터 삭제 후 데이터 갯수 = 0
        assertTrue(memberDao.count()==0);
    }

     /* countIDTest
      * - 아이디로 카운팅 했을 때 중복 아이디 있으면 1 리턴
      * - 아이디로 카운팅 했을 때 중복 아이디 없으면 0 리턴
      */
    @Test
    void countIDTest() {
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count()==0);

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
                "qwert1234",
                "N");

        /* 1. 아이디로 카운팅 했을 때 중복 아이디 있으면 1 리턴 */
        // 회원 1명 생성 후, 같은 아이디 count 하면 1 리턴
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        assertTrue(memberDao.countID("asdf1234")==1);


        /* 2. 아이디로 카운팅 했을 때 중복 아이디 없으면 0 리턴 */
        // 모든 데이터 삭제 후 데이터 갯수 = 0
        assertTrue(memberDao.deleteAll()==1);
        assertTrue(memberDao.count()==0);
        // 회원 1명 생성 후, 다른 아이디 count 하면 0 리턴
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        assertTrue(memberDao.countID("qwer1234")==0);


        // 모든 데이터 삭제 후 데이터 갯수 = 0
        assertTrue(memberDao.deleteAll()==1);
        assertTrue(memberDao.count()==0);
    }

    /* countEmailTest
     * - 이메일로 카운팅 했을 때 중복 이메일 있으면 1 리턴
     * - 이메일로 카운팅 했을 때 중복 이메일 없으면 0 리턴
     */
    @Test
    void countEmailTest() {
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count()==0);

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
                "qwert1234",
                "N");

        /* 1. 이메일로 카운팅 했을 때 중복 이메일 있으면 1 리턴 */
        // 회원 1명 생성 후, 같은 이메일 count 하면 1 리턴
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        assertTrue(memberDao.count()==1);
        assertTrue(memberDao.countEmail("asdf@naver.com")==1);


        /* 2. 아이디로 카운팅 했을 때 중복 아이디 없으면 0 리턴 */
        // 모든 데이터 삭제 후 데이터 갯수 = 0
        assertTrue(memberDao.deleteAll()==1);
        assertTrue(memberDao.count()==0);
        // 회원 1명 생성 후, 다른 이메일 count 하면 0 리턴
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        assertTrue(memberDao.count()==1);
        assertTrue(memberDao.countEmail("qwer@naver.com")==0);

        // 모든 데이터 삭제 후 데이터 갯수 = 0
        assertTrue(memberDao.deleteAll()==1);
        assertTrue(memberDao.count()==0);
    }


    /* 아이디로 회원 정보 삭제 테스트
     * 1. 아이디가 같은 경우 삭제 성공
     * 2. 아이디가 회원정보에 없는 경우 삭제 실패
     */
    @Test
    void deleteByMemberIDTest() {
        // 회원 정보를 모두 삭제 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count()==0);

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

        // 새로운 회원 1명 insert
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        // 1. 아이디가 같은 경우 삭제 성공
        assertTrue(memberDao.deleteByMemberID("asdf1234")==1);
        // 회원 정보를 지운 후, 데이터 총 갯수 = 0
        assertTrue(memberDao.count()==0);

        // 새로운 회원 1명 insert
        assertTrue(memberDao.insertMemberAllInfo(memberDto)==1);
        // 2. 아이디가 회원정보에 없는 경우 삭제 실패
        assertTrue(memberDao.deleteByMemberID("asdf123")==0);
        // 회원 정보 삭제 실패 후, 남은 회원 갯수 = 1
        assertTrue(memberDao.count()==1);

    }


    /* 아이디를 이용해서 회원정보 조회 테스트 *
     *  1. 회원 정보 1개 insert 후, 존재하는 조회 테스트
     *  2. 회원 정보 1개 insert 후, 존재하지 않는 id로 조회 테스트
     *  3. 회원 정보 2개 insert 후, 존재하는 id로 조회 테스트
     */
    @Test
    void selectByMemberIDTest() {
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

        /* 1. 회원 정보 1개 insert 후, 존재하는 조회 테스트 */
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 존재하는 회원 조회 성공
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getId().equals("asdf1234"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

        /* 2. 회원 정보 1개 insert 후, 존재하지 않는 id로 조회 테스트 */
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 존재하지 않는 회원 조회시 값이 null
        assertTrue(memberDao.selectMemberByMemberId("asdf123")==null);
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

        /* 3. 회원 정보 2개 insert 후, 존재하는 id로 조회 테스트 */
        // 회원 1명 등록 성공 후 데이터 총 갯수 1
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        MemberDto memberDto2 = new MemberDto(
                "40001",
                "qwerty1234",
                "qwerty1234!",
                "우소미",
                "1992-03-31",
                "F",
                "01098765432",
                "qwerty@naver.com",
                "",
                "N");
        // 2번째 회원 등록 성공 후 데이터 총 갯수 2
        assertTrue(memberDao.insertMemberAllInfo(memberDto2) == 1);
        assertTrue(memberDao.count() == 2);
        // 존재하는 회원 조회 성공
        assertTrue(memberDao.selectMemberByMemberId("qwerty1234").getId().equals("qwerty1234"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

    }

    /* selectMemberByMemberIdPasswordTest
     * - 아이디를 이용해서 있을 경우, 조회 성공
     * - 아이디를 이용해서 없을 경우, 조회 실패
     * - 회원가입 2개 있을 때 아이디로 조회 성공
     */
    @Test
    void selectByMemberIDPasswordTest() {
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

    /* 1. 회원 정보 1개 insert 후, 존재하는 조회 테스트 */
    // 회원 1명 등록 성공 후 데이터 총 갯수 1
    assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
    assertTrue(memberDao.count() == 1);
    // 존재하는 회원 조회 성공
    assertTrue(memberDao.selectMemberByMemberIdPassword("asdf1234", "asdf1234!").getId().equals("asdf1234"));
    // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
    assertTrue(memberDao.count() == 0);

    /* 2. 회원 정보 1개 insert 후, 존재하지 않는 id로 조회 테스트 */
    // 회원 1명 등록 성공 후 데이터 총 갯수 1
    assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
    assertTrue(memberDao.count() == 1);
    // 존재하지 않는 회원 조회시 값이 null
    assertTrue(memberDao.selectMemberByMemberIdPassword("asdf123", "asdf1234!")==null);
    // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
    memberDao.deleteAll();
    assertTrue(memberDao.count() == 0);

    /* 3. 회원 정보 2개 insert 후, 존재하는 id로 조회 테스트 */
    // 회원 1명 등록 성공 후 데이터 총 갯수 1
    assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
    assertTrue(memberDao.count() == 1);
    MemberDto memberDto2 = new MemberDto(
            "40001",
            "qwerty1234",
            "qwerty1234!",
            "우소미",
            "1992-03-31",
            "F",
            "01098765432",
            "qwerty@naver.com",
            "",
            "N");
    // 2번째 회원 등록 성공 후 데이터 총 갯수 2
    assertTrue(memberDao.insertMemberAllInfo(memberDto2) == 1);
    assertTrue(memberDao.count() == 2);
    // 존재하는 회원 조회 성공
    assertTrue(memberDao.selectMemberByMemberIdPassword("qwerty1234", "qwerty1234!").getId().equals("qwerty1234"));
    // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
    assertTrue(memberDao.count() == 0);

}


    /* 모든 회원 리스트 가져오는 테스트
     * 1. 모든 회원 리스트 가져오는 테스트 - '관리자' 정보
     * 2. 모든 회원 리스트 가져오는 테스트 - '일반회원' 정보
     */
    @Test
    void selectAllMemberByMemberId() {
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count() == 0);
//
//        // MemberDto 생성
//        MemberDto memberDto = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        // MemberDto 생성
//        MemberDto memberDto2 = new MemberDto(
//                "40001",
//                "qwer1234",
//                "qwer1234!",
//                "우소미",
//                "1992-03-31",
//                "F",
//                "01098765432",
//                "qwer@naver.com",
//                "",
//                "N");
//        // MemberDto 생성
//        MemberDto memberDto3 = new MemberDto(
//                "40001",
//                "zxcv1234",
//                "zxcv1234!!",
//                "문연지",
//                "1998-06-22",
//                "F",
//                "01023456789",
//                "zxcv@naver.com",
//                "",
//                "N");
//
//        /* 1. 회원 정보 3개 insert 후, 일반 회원 리스트 조회 테스트 */
//        // 회원 3명 등록 성공 후 데이터 총 갯수 3
//        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2) == 1);
//        assertTrue(memberDao.insertMemberAllInfo(memberDto3) == 1);
//        assertTrue(memberDao.count() == 3);
//        // 일반 회원 조회 성공 및 갯수 비교
//        List<MemberDto> memberDtoList = new ArrayList<>(memberDao.selectAllMemberByMemberId("N"));
//        assertTrue(memberDtoList.size() == 3);
//        //assertTrue(memberDtoList);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count() == 0);
//
//        /* 2. 회원 정보 3개 insert 후, 관리자 리스트 조회 테스트 */
//        // 회원 3명 등록 성공 후 데이터 총 갯수 3
//        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2) == 1);
//        assertTrue(memberDao.insertMemberAllInfo(memberDto3) == 1);
//        assertTrue(memberDao.count() == 1);
//        // 존재하는 회원 조회 성공
//        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getId().equals("asdf1234"));
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count() == 0);
//
   }


    /* 회원 id로 회원 정보 수정 테스트
     * 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공
     * 2. 회원 id로 회원 정보(다중 필드) 업데이트 성공
     */
    @Test
    void updateMemberInfoByMemberId() {
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

        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-1. 회원 상태 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        MemberDto memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getMember_state_code().equals("40001"));
        // '회원 상태 코드' 수정
        memberDto2.setMember_state_code("40002");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getMember_state_code().equals("40002"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-2. 패스워드 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getPassword().equals("asdf1234!"));
        // 비밀번호 수정
        memberDto2.setPassword("asdf123!");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getPassword().equals("asdf123!"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-3. 이름 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getName().equals("김진철"));
        // 이름 수정
        memberDto2.setName("김삼성");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getName().equals("김삼성"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-4. 생년월일 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getBirth().equals("2000-08-22"));
        // birth 수정
        memberDto2.setBirth("2000-08-21");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getBirth().equals("2000-08-21"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-5. sex 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getSex().equals("M"));
        // sex 수정
        memberDto2.setSex("F");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getSex().equals("F"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-6. phone_number 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getPhone_number().equals("01012345678"));
        // phone_number 수정
        memberDto2.setPhone_number("01000000000");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getPhone_number().equals("01000000000"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-7. email 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getEmail().equals("asdf@naver.com"));
        // email 수정
        memberDto2.setEmail("asdf1234@naver.com");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getEmail().equals("asdf1234@naver.com"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-8. login_datetime 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getLogin_datetime()==null);
        // login_datetime 수정
        memberDto2.setLogin_datetime("2023-03-21 19:00:34");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getLogin_datetime().equals("2023-03-21 19:00:34"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-9. register_datetime 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getRegister_datetime()!=null);
        // modify_datetime 수정
        memberDto2.setRegister_datetime("2023-03-21 19:00:34");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getRegister_datetime().equals("2023-03-21 19:00:34"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-10. exit_datetime 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getExit_datetime().equals("9999-12-31 23:59:59"));
        // exit_datetime 수정
        memberDto2.setExit_datetime("2023-03-21 19:00:34");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getExit_datetime().equals("2023-03-21 19:00:34"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-11. recommand_id 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getRecommand_id().equals(""));
        // recommand_id 수정
        memberDto2.setRecommand_id("qwerty1234");
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getRecommand_id().equals("qwerty1234"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 */
        // 1-12. login_count 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getLogin_count()==0);
        // login_count 수정
        memberDto2.setLogin_count(1);
        assertTrue(memberDao.updateMemberInfoByMemberId(memberDto2)==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getLogin_count()==1);
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);


        /* 1. 회원 id로 회원 정보(단일 필드) 업데이트 성공 - 관리자 변경 */
        // 1-13. is_admin 수정
        // 회원 1명 삽입 성공
        assertTrue(memberDao.insertMemberAllInfo(memberDto) == 1);
        assertTrue(memberDao.count() == 1);
        // 입력한 회원 id로 상태 확인
        memberDto2 = memberDao.selectMemberByMemberId("asdf1234");
        assertTrue(memberDto2.getIs_admin().equals("N"));
        // is_admin 수정
        assertTrue(memberDao.updateIsAdminByMemberId("asdf1234", "Y")==1);
        // 정보 수정 되었는지 확인
        assertTrue(memberDao.selectMemberByMemberId("asdf1234").getIs_admin().equals("Y"));
        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
        memberDao.deleteAll();
        assertTrue(memberDao.count() == 0);

    }


//    /* 예외 처리 - 회원 정보가 넘어가지 않았을 때  */
//    @Test
//    void MemberAllInfoTest() {
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2. 회원 등록 때 1개의 정보가 빈문자열이 등록되었을 때 실패 (각 컬럼마다 제외)
//        // 2-1. member_state_code
//        MemberDto memberDto2 = new MemberDto(
//                "",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        // 회원 등록 실패
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==0);
//        // 회원 등록 후, 데이터 총 갯수 = 0
//        assertTrue(memberDao.count()==0);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        //memberDao.deleteAll();
//        //assertTrue(memberDao.count()==0);
//
//        // 2-2. password
//        MemberDto memberDto3 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==0);
//        // 회원 등록 후, 데이터 총 갯수 = 0
//        assertTrue(memberDao.count()==0);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-3. name
//        MemberDto memberDto4 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getName()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-4. birth
//        MemberDto memberDto5 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getBirth()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-5. sex
//        MemberDto memberDto6 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getSex()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-6. phone_number
//        MemberDto memberDto7 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "",
//                "asdf@naver.com",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getPhone_number()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-7. email
//        MemberDto memberDto8 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "",
//                "qwert1234",
//                "N");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getEmail()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//        // 2-8. is_admin
//        MemberDto memberDto10 = new MemberDto(
//                "40001",
//                "asdf1234",
//                "asdf1234!",
//                "김진철",
//                "2000-08-22",
//                "M",
//                "01012345678",
//                "asdf@naver.com",
//                "qwert1234",
//                "");
//        assertTrue(memberDao.insertMemberAllInfo(memberDto2)==1);
//        // 회원 등록 후, 데이터 총 갯수 = 1
//        assertTrue(memberDao.count()==1);
//        // 빈 문자열이면
//        if((memberDao.findMemberByMemberId("asdf1234").getIs_admin()).equals(""))
//            assertTrue(false);
//        // 회원 정보를 모두 지운 후, 데이터 총 갯수 = 0
//        memberDao.deleteAll();
//        assertTrue(memberDao.count()==0);
//
//    }
}