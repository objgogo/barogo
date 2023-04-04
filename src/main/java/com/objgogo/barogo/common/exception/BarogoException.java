package com.objgogo.barogo.common.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarogoException extends RuntimeException{

    private String message;
    private String code;

    public BarogoException(String message, String code){
        this.message = message;
        this.code = code;
    }
}
