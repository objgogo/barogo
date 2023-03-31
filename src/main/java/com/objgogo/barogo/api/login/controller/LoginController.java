package com.objgogo.barogo.api.login.controller;

import com.objgogo.barogo.api.login.service.LoginService;
import com.objgogo.barogo.api.login.vo.LoginRequest;
import com.objgogo.barogo.common.provider.JwtTokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/login")
@Api(tags ={"[사용자] - Login API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
public class LoginController {


    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "Test Api")
    @GetMapping("/test")
    public void test(){
        System.out.println("hello objgogo");
    }


    @PostMapping("")
    @ApiOperation(value = "사용자 로그인")
    public ResponseEntity<JwtTokenResponse> login(@RequestBody LoginRequest req){
        return ResponseEntity.ok(loginService.login(req));

    }
}
