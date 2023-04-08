package com.objgogo.barogo.common.exception;


import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Getter
@Setter
public class BarogoException extends RuntimeException{


    private String code;

    public BarogoException(String code){
        super(getMessageByCode(code));
        this.code = code;
    }

    public BarogoException(String code, Object... message){

        super(getMessagesByCode(code,message));
        this.code = code;




    }

    private static String getMessageByCode(String code){
        ResourceBundle bundle = ResourceBundle.getBundle("error");
        String msg = "";
        try {
            msg = bundle.getString(code);
        } catch (Exception e) {
            msg = "Unknown error occurred.";
        }
        return msg;
    }

    private static String getMessagesByCode(String code, Object... message){
        ResourceBundle bundle = ResourceBundle.getBundle("error");
        String msg = bundle.getString(code);

        return MessageFormat.format(msg,message);
    }
}
