package com.objgogo.barogo.api.account.controller;

import com.objgogo.barogo.api.account.service.AccountService;
import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.api.account.vo.RegisterUserResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@Api(tags ={"[사용자] - Account API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
@RequestMapping("/api/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    public ResponseEntity<RegisterUserResponse> registerAccount(@Valid @RequestBody RegisterUserRequest req){
        return ResponseEntity.ok(accountService.registerUser(req));
    }
}
