package com.fastcampus.toyproject2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
public class GlobalRestControllerAdvice {

    //@Valid 애너테이션으로 받은거.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder//append(fieldError.getField())
                    .append(fieldError.getDefaultMessage())
                    .append(" 입력된 값: ")
                    .append(fieldError.getRejectedValue());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message",builder.toString()));
    }

}