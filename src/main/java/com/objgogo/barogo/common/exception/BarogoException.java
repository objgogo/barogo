package com.objgogo.barogo.common.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

@Getter
@Setter
public class BarogoException extends RuntimeException{


    private String code;

    public BarogoException(String code){
        super(getMessageByCode(code));
        this.code = code;
    }

    private static String getMessageByCode(String code){
        ResourceBundle bundle = ResourceBundle.getBundle("error");
        String message = "";
        try {
            message = bundle.getString(code);
        } catch (Exception e) {
            message = "Unknown error occurred.";
        }
        return message;
    }
}
