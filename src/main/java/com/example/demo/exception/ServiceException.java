package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private int code;
    private String msg;
    private ExceptionTypeEnum typeEnum;
}
