package com.objgogo.barogo.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class SearchUtil {

    public static boolean isDateRangeValid(LocalDate startDt, LocalDate endDt, Long standard){

        if(startDt.compareTo(endDt) > 0){
            return false;
        } else{
            long betweenDays = ChronoUnit.DAYS.between(startDt,endDt);
            return standard >= Math.abs(betweenDays);
        }
    }
}
