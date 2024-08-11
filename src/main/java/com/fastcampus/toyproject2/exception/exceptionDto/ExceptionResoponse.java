package com.fastcampus.toyproject2.exception.exceptionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ExceptionResoponse {


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class ExceptionResponse {
        private LocalDateTime time;
        private Boolean isSuccess;
        private String message;
        private String details;
    }
}
