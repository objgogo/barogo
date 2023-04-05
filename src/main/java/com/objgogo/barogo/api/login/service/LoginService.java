package com.objgogo.barogo.api.login.service;

import com.objgogo.barogo.api.login.vo.LoginRequest;
import com.objgogo.barogo.common.provider.JwtTokenResponse;

public interface LoginService {

    JwtTokenResponse login(LoginRequest req);

}
