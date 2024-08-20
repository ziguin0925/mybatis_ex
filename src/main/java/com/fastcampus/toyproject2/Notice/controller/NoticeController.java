package com.fastcampus.toyproject2.Notice.controller;



import com.fastcampus.toyproject2.Notice.dto.NoticeDto;
import com.fastcampus.toyproject2.Notice.service.NoticeService;
import com.fastcampus.toyproject2.Notice.PageHandler;
import com.fastcampus.toyproject2.Validator.DtoCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO
//1. 로그인 기능 추가되면 id여부, admin여부 - role=의 attribute를 session에서 가져온다.
//2. 사진 기능 추가시 (1개의 tx에서 먼저 공지data생성하고 공지사진 data생성)

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor //private final인 객체에 대해서 생성자 만들어줌(생성자 주입방식) - autowired를 대체
public class NoticeController {
    private final NoticeService noticeService;

    //1.입력화면(get, insert)
    @GetMapping("/insert")
    public String insert(){
        try{
            //TODO: 관리자 validation - 매개변수에 미리 구현해놓기(가짜값으로라도 해놓기)
            //관리자 판별 validation
            //1) session이 있는지, session에서 is_admin값이 있는지, is_admin의 값이 Y인지 확인
            //2) 아니면 예외 발생하고 list로 redirect
            //3) 맞으면 model에 member_number 저장하고 insert.html
//            HttpSession session = request.getSession(false);
//            if(!(session!= null && session.getAttribute("is_admin") != null && session.getAttribute("is_admin").equals("Y"))){
//                System.out.println ("ADMIN X");
//            } else{
//                System.out.println("ADMIN CHECKED");
//
//            }
//            if(!(is_admin.equals("Y"))) throw new Exception("Not Authorized");
//            if(AdminCheck.adminCheck(request) != true) throw new Exception("Not Authorized");
            //1)session에서 is_admin 값이 Y인지 판별한다.
//            String is_admin = (String)session.getAttribute("is_admin");
//            Integer member_number = (Integer)session.getAttribute("memeber_number");
//            System.out.println("is_admin = " + is_admin);
//            System.out.println("member_number = " + member_number);
            //2)아니면 예외 발생
            //3)맞으면 model에 memeber_number 저장하고 insert.html
//            m.addAttribute("member_number", member_number);
            return "insert";
        }catch(Exception e) {
            //관리자가 아니면 list로 redirect한다.
//            e.printStackTrace(); //에러 내용 출력
//            rattr.addFlashAttribute("msg", "NOT_ADMIN"); //에러 메시지
            return "redirect:/notice/list";
        }
    }

    //1-2.저장(post, insert)
    @PostMapping("/insert") //예외처리(멤버 정보 못가져왔을때)
    public String insert(NoticeDto noticeDto, Model m, RedirectAttributes rattr){
        //TODO : 관리자 validation
        //1) session이 있는지, session에서 is_admin값이 있는지, is_admin의 값이 Y인지 확인
        //2) 아니면 예외 발생하고 list로 redirect
        //3) 맞으면 noticeDto의 writer에 member_number 저장하고(noticeDto.setWriter(writer)) insert.html
        try {
            //도메인 검증
            DtoCheck.validateNoticeDto(noticeDto);

            //1)noticeDto 삽입, 실패시 예외 던지기
            if (noticeService.insert(noticeDto) != 1) throw new Exception("Service.insert() failed");
            //2)성공시 메시지 addFlashAttribute
            rattr.addFlashAttribute("msg", "INSERT_OK");
            //3)list 로 redirect
            return "redirect:/notice/list";
        } catch (Exception e) {
            e.printStackTrace(); //에러 내용 출력
            m.addAttribute(noticeDto); //입력한 내용 기억
            m.addAttribute("msg", "INSERT_ERR"); //에러 메시지
            return "insert"; //등록하려던 내용을 보여줘야하므로 insert.html
        }
    }

    //2.리스트조회(list) - 모두 볼 수 있다
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize,Model m){
        try {
            //pagination
            //1)게시글수 파악
            int totalCnt = noticeService.getCount();
            m.addAttribute("totalCnt", totalCnt);

            //2)페이지핸들러 호출
            PageHandler ph = new PageHandler(totalCnt, page, pageSize);

            if(page < 0 || page > ph.getTotalPage())
                page = 1;
            if(pageSize < 0 || pageSize > 50)
                pageSize = 10;

            //3)map에 offset, pageSize 저장(일회용인거 기억하기)
            Map map = new HashMap();
            map.put("offset", (page - 1) * pageSize);
            map.put("pageSize", pageSize);

            //4)공지 상단 게시 리스트
            List<NoticeDto> noticeDtoTopPostList = noticeService.selectAllTopPost();
            m.addAttribute("noticeDtoTopPostList", noticeDtoTopPostList);

            //5) 공지 하단 게시 리스트
            List<NoticeDto> noticeDtoDescPageList = noticeService.selectAllDescPage(map);
            if(noticeDtoDescPageList.isEmpty()) throw new Exception("Service.pagelist() failed");
            m.addAttribute("noticeDtoDescPageList", noticeDtoDescPageList);
            m.addAttribute("pageHandler", ph);
        } catch (Exception e) {
            e.printStackTrace();
            //6)리스트가 비어있거나 가져오는데 실패하면 에러 메시지
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }
        //7)성공이든 실패든 공지사항 리스트를 보여준다.
        return "list"; //6) list.html
    }

    //3.단일 조회 - 모두 볼 수 있다
    @GetMapping("/{id}")
    public String selectById(@PathVariable("id") Long id, Model m, RedirectAttributes rattr){
        try{
            NoticeDto noticeDto = noticeService.selectById(id); //1) id로 해당 noticeDTO찾기
            if(noticeDto == null) {
                throw new Exception("service.selectbyid not found"); //1-1) 못찾으면 에러 표시하고 나간다.
            }
            m.addAttribute("notice", noticeDto);    // 2) noticeDTO 모델에 저장
        } catch (Exception e) {
            e.printStackTrace(); //1)에러 확인
            rattr.addFlashAttribute("msg", "READ_ERR"); //2)에러 메시지
            return "redirect:/notice/list"; //3) 리스트로 리다이렉트
        }
        return "detail"; //3)detail.html 이동
    }

    //4.update 화면 이동 - 관리자만 가능, 예외처리
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model m, RedirectAttributes rattr){
        //TODO : 관리자 validation
        //관리자 여부 확인
        //관리자 아니면 list로 redirect
        //관리자이면 진행
        try{
            NoticeDto noticeDto = noticeService.selectById(id); //1) id로 update할 noticeDTO찾기
            if(noticeDto == null) {
                throw new Exception("update select not found"); //1-1) 못찾으면 에러 표시하고 나간다.
            }
            m.addAttribute("notice", noticeDto); //2)모델에 추가
            return "update";
        } catch (Exception e) {
            e.printStackTrace(); //에러 확인
            rattr.addFlashAttribute("msg", "UPDATE_MV_ERR"); //에러 표시
            return "redirect:/notice/list"; //못찾거나 update작성 화면으로 못넘어가므로 list로 redirect
        }
    }

    //4-2.update 요청(POST) - 관리자만 가능, 예외처리
    @PostMapping("/update/{id}")
    public String update(NoticeDto noticeDto, Model m, RedirectAttributes rattr) {
        //TODO : 관리자 validation
        //관리자 여부 확인
        //관리자 아니면 list로 redirect
        //관리자이면 진행
        //관리자의 id 저장, dto.setModifier(id)
        try{
            //도메인 검증
            DtoCheck.validateNoticeDto(noticeDto);

            if(noticeService.update(noticeDto) != 1) throw new Exception("service.update() failed");    //1. noticeDTO update실행
            rattr.addFlashAttribute("msg", "UPDATE_OK");                       //2. 성공시 메시지 보내기
            return "redirect:/notice/{id}";                                                              //3. 상세화면으로 돌아간다.
        } catch (Exception e) {
            e.printStackTrace();//1. 실패시 에러 출력
            m.addAttribute(noticeDto); //입력한 내용 기억
            m.addAttribute("msg", "UPDATE_ERR");                               //2. 에러 메시지 보내기
            return "update";                                                                            //3. 수정화면으로 돌아간다.
        }
    }

    //5.삭제 요청(GET) - 관리자만 가능, 예외처리
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model m, RedirectAttributes rattr){
        //TODO : 관리자 validation
        //관리자 여부 확인
        //관리자 아니면 list로 redirect
        //관리자이면 진행
        //관리자의 id 저장, dto.setModifier(id)
        try{
            if(noticeService.delete(id) != 1) throw new Exception("service.delete() failed"); //1) 해당 id delete() 호출
            rattr.addFlashAttribute("msg", "DELETE_OK");             //2) 성공 메시지 보내기
            return "redirect:/notice/list";                                                   //3) 리스트로 돌아간다.
        } catch (Exception e) {
            e.printStackTrace();                                                              //1) 실패시 에러 출력
            rattr.addFlashAttribute("msg", "DELETE_ERR");            //2) 에러 메시지 보내기
            return "detail";                                                                  //3) 상세 화면으로 돌아가기
        }
    }
}