package com.objgogo.barogo.common.aop;

import com.objgogo.barogo.common.annotaion.PossibleAccess;
import com.objgogo.barogo.common.exception.BarogoException;
import com.objgogo.barogo.common.provider.JwtTokenProvider;
import com.objgogo.barogo.common.util.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Aspect
@Component
public class CheckAuth {


    private JwtTokenProvider jwtTokenProvider;

    public CheckAuth(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Before("@annotation(com.objgogo.barogo.common.annotaion.PossibleAccess)")
    public void checkPossibleAccess(JoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        PossibleAccess possibleAccess = signature.getMethod().getAnnotation(PossibleAccess.class);
        String possibleAccessType = "ROLE_" + possibleAccess.value(); // PossibleAccess 어노테이션의 value 값 가져오기

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("Authorization");

        Authentication tmp = jwtTokenProvider.getAuthentication(token);
        UserDetails user = (UserDetails) tmp.getPrincipal();

        Collection<? extends GrantedAuthority> authorities =  user.getAuthorities();

        boolean check = false;
        for(GrantedAuthority a : authorities){
            if(a.getAuthority().equals(possibleAccessType)){
                check = true;
                break;
            }
        }

        if(!check){
            throw new BarogoException("ERROR.ACCESS.000",possibleAccessType);
        }




    }
}
