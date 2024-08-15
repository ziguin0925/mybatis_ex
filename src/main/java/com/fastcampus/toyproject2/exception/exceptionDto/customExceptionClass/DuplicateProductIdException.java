package com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass;

import lombok.Getter;

@Getter
public class DuplicateProductIdException extends RuntimeException {

    private String message;

    public DuplicateProductIdException(String message) {
        this.message = message;
    }
}