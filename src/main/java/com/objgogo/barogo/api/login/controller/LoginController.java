package com.objgogo.barogo.api.login.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/login")
public class LoginController {


    @ApiOperation(value = "Test Api")
    @GetMapping("/test")
    public void test(){
        System.out.println("hello objgogo");
    }
}
