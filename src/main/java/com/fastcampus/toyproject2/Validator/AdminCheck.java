package com.fastcampus.toyproject2.Validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AdminCheck {
    public static boolean adminCheck(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return session!= null && session.getAttribute("is_admin") != null && session.getAttribute("is_admin").equals("Y");
    }
}
