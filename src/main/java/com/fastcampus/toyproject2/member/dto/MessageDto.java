package com.fastcampus.toyproject2.member.dto;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


public class MessageDto {

    private String message;              // 사용자에게 전달할 메시지
    private String redirectUri;          // 리다이렉트 URI
    private RequestMethod method;        // HTTP 요청 메서드
    private Map<String, Object> data;    // 화면(View)으로 전달할 데이터(파라미터)

    public MessageDto(String message, String redirectUri, RequestMethod method, Map<String, Object> data) {
        this.message = message;
        this.redirectUri = redirectUri;
        this.method = method;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "message='" + message + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", method=" + method +
                ", data=" + data +
                '}';
    }
}

