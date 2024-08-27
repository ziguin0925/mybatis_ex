package com.fastcampus.toyproject2.exception;


import com.fastcampus.toyproject2.exception.exceptionDto.ExceptionResoponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.*;

/*
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalRestControllerAdvice.class);

    //@Valid 애너테이션으로 받은거.
    //ExceptionDto 사용해서 반환하도록. -- 사용자에게 예외 시간을 보여줄 필요가 있나?
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String,Object>> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

//        StringBuilder builder = new StringBuilder();
        List<ExceptionResoponse> exceptionResoponses = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder//append(fieldError.getField())
//                    .append(fieldError.getDefaultMessage())
//                    .append(" 입력된 값: ")
//                    .append(fieldError.getRejectedValue());


            exceptionResoponses.add(ExceptionResoponse.builder()
                    .isSuccess(false)
                    .message(fieldError.getDefaultMessage()).build());

        }
        HashMap<String,Object> map = new HashMap<>();
        map.put("exception",exceptionResoponses);

        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<HashMap<String,Object>> processConnectException(ConnectException exception) {
        HashMap<String,Object> map = new HashMap<>();
        ExceptionResoponse exceptionResoponse =ExceptionResoponse.builder()
                                                    .isSuccess(false)
                                                    .message("Network 에러").build();

        System.out.println(Arrays.toString(exception.getStackTrace()));
        System.out.println(exception.getMessage());

        map.put("exception",exceptionResoponse);
        return ResponseEntity.badRequest().body(map);
    }

}*/