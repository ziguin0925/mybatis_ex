package com.fastcampus.toyproject2.member.controller;


import com.fastcampus.toyproject2.member.dto.LoginDto;
import com.fastcampus.toyproject2.member.dto.MessageDto;
import com.fastcampus.toyproject2.member.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String showLogin() {
        return "login"; // login.html
    }

    @PostMapping("/login")
//    public String login(@RequestParam("id") String id, @RequestParam("password") String password, Model m){
    public String login(@Valid LoginDto loginDto, BindingResult bindingResult, Model model) {
        // LoginDto가 null일 수 없음
//        if (loginDto == null) {
//            model.addAttribute("errorMsg", "Login information is missing");
//            return "login"; // 로그인 페이지로 돌아감
//        }

        /* 0. 유효성 검사 */
        // 0-1. 아이디나 비밀번호를 빈칸으로 입력한 후, 로그인 하면
        // 0-2. 아이디나 비밀번호 size 맞지 않는 경우, 로그인 하면
        if (bindingResult.hasErrors()) {
//            model.addAttribute("errorMsg", getErrors(bindingResult));
//            return "login"; // 로그인 페이지로 돌아감
            MessageDto message = new MessageDto("아이디나 비밀번호를 입력하여 주십시오.", "login", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }

        // 1. id로 회원 정보 가져오기
        LoginDto checkLoginDto = loginService.readMemberById(loginDto.getId());
        // 1-1. id로 조회되지 않는다면(회원이 아니라면)
        if(checkLoginDto == null) {
            MessageDto message = new MessageDto("일치하는 회원 정보가 없습니다.", "login", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }

        // 2. ID와 비밀번호를 일치하는지 확인
        if(checkLoginDto!=null && loginCheck(checkLoginDto, loginDto.getPassword())){
            // 2-1. 일치하면, index.html
            return "index";

        } else {  // 2-2. 비밀번호가 일치하지 않는다면
            // 메시지 보여주기
            MessageDto message = new MessageDto("아이디나 비밀번호가 일치하지 않습니다. ", "login", RequestMethod.GET, null);
            return showMessageAndRedirect(message, model);
        }

    }

    // 로그인 정보가 맞는지 확인
    private boolean loginCheck(LoginDto loginDto, String password) {
        return password.equals(loginDto.getPassword());
    }

    // BindingResult에서 오류 메시지 추출
    private Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }

    // 사용자에게 메시지를 전달 및 페이지를 리다이렉트
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "messageRedirect";
    }

}
