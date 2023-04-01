package com.objgogo.barogo.common.util;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.provider.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class UserUtil {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;

    public UserUtil(JwtTokenProvider jwtTokenProvider, AccountRepository accountRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountRepository = accountRepository;
    }

    public AccountEntity me(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("Authorization");

        Authentication tmp = jwtTokenProvider.getAuthentication(token);
        UserDetails user = (UserDetails) tmp.getPrincipal();

        Optional<AccountEntity> userEntity = accountRepository.findByUsername(user.getUsername());

        return userEntity.get();
    }

    public Boolean checkAuthority(UserType type){

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("Authorization");

        Authentication tmp = jwtTokenProvider.getAuthentication(token);
        UserDetails user = (UserDetails) tmp.getPrincipal();

        Collection<? extends GrantedAuthority> authorities =  user.getAuthorities();

        for(GrantedAuthority a : authorities){
            if(a.getAuthority().equals(type.toString())){
                return true;
            }
        }
        return false;
    }


}
