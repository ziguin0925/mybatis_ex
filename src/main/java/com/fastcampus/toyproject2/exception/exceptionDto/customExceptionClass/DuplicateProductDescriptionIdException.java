package com.fastcampus.toyproject2.exception.exceptionDto.customExceptionClass;

import lombok.Getter;

@Getter
public class DuplicateProductDescriptionIdException extends RuntimeException{
    private String message;

    public DuplicateProductDescriptionIdException(String message) {
        this.message = message;
    }
}
