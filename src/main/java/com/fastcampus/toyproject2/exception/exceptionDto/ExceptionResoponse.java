package com.fastcampus.toyproject2.exception.exceptionDto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExceptionResoponse {



//        private LocalDateTime time;
        private Boolean isSuccess;
        private String message;
//        private String details;
}
