package com.fastcampus.toyproject2.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NO_FILE_EXTENTION(404, "파일의 확장자가 올바르지 않습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
