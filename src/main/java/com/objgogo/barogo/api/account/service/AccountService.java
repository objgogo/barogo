package com.objgogo.barogo.api.account.service;

import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.api.account.vo.RegisterUserResponse;

public interface AccountService {

    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
}
