package com.fastcampus.toyproject2.exception.exceptionDto;

import com.fastcampus.toyproject2.exception.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExceptionResoponse {



        private String message;
        private String code;
        private int status;
        private String detail;

        public ExceptionResoponse(ErrorCode code) {
                this.message = code.getMessage();
                this.status = code.getStatus();
                this.code = code.getCode();
                this.detail = code.getDetail();
        }

        public static ExceptionResoponse of(ErrorCode  code) {
                return new ExceptionResoponse(code);
        }
}
