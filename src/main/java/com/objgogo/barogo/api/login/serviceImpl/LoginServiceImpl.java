package com.objgogo.barogo.api.login.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.api.login.service.LoginService;
import com.objgogo.barogo.api.login.vo.LoginRequest;
import com.objgogo.barogo.common.provider.JwtTokenProvider;
import com.objgogo.barogo.common.provider.JwtTokenResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtTokenResponse login(LoginRequest req) throws Exception {

        Optional<AccountEntity> userInfo = accountRepository.findByUsername(req.getUsername());

        if(userInfo.isPresent()){
            AccountEntity user = userInfo.get();

            if(passwordEncoder.matches(req.getPassword(),user.getPassword())){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword(),user.getAuthorities());
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);

                return jwtTokenProvider.generateToken(authentication);
            } else{
                throw new Exception("비밀번호 틀림");
            }
        } else {
            throw new Exception("아이디 정보가 없음");
        }





    }
}
