package com.fastcampus.toyproject2.Validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCheck {
    public boolean loginCheck(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("member_number") != null;
    }
}
