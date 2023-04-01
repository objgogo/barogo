//package com.objgogo.barogo;
//
//import com.objgogo.barogo.api.account.entity.AccountEntity;
//import com.objgogo.barogo.api.account.repository.AccountRepository;
//import com.objgogo.barogo.api.account.service.AccountService;
//import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
//import com.objgogo.barogo.api.account.vo.RegisterUserResponse;
//import com.objgogo.barogo.api.login.service.LoginService;
//import com.objgogo.barogo.api.login.vo.LoginRequest;
//import com.objgogo.barogo.common.UserType;
//import com.objgogo.barogo.common.provider.JwtTokenProvider;
//import com.objgogo.barogo.common.provider.JwtTokenResponse;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//
//@SpringBootTest
//class BarogoApplicationTests {
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//
//
//    @Test
//    void contextLoads() {
//        System.out.println("test");
//    }
//
//    //TODO: RegisterUserRequest 객체에 password를 직접 set 하므로 validation 체크를 무시하게 됨.
//    //TODO: API 호출 Test로 재 검증
//    @Test
//    void modelMapperTest(){
//
//        AccountEntity entity = new AccountEntity();
//        RegisterUserRequest info = new RegisterUserRequest();
//
//        info.setName("test");
//        info.setPassword("1234");
//        info.setUsername("objgogo");
//        info.setRoles(List.of(new String[]{"USER", "ADMIN"}));
//
//        ModelMapper mapper = new ModelMapper();
//
//        entity = mapper.map(info,AccountEntity.class);
//
//        System.out.println(entity.toString());
//
//    }
//
//    @Test
//    void saveAccount(){
//
//        RegisterUserRequest info = new RegisterUserRequest();
//
//        info.setName("test");
//        info.setPassword("1234");
//        info.setUsername("objgogo");
//        info.setRoles(List.of(new String[]{"USER", "ADMIN"}));
//
//        RegisterUserResponse res = accountService.registerUser(info);
//
//        System.out.println(res.toString());
//
//    }
//
//    @Test
//    void loginTrue() throws Exception {
//        RegisterUserRequest info = new RegisterUserRequest();
//
//        info.setName("test");
//        info.setPassword("1234");
//        info.setUsername("objgogo");
//        info.setRoles(List.of(new String[]{"USER", "ADMIN"}));
//
//        RegisterUserResponse res = accountService.registerUser(info);
//
//        LoginRequest req = new LoginRequest();
//
//        req.setUsername("objgogo");
//        req.setPassword("1234");
//
//        JwtTokenResponse result = loginService.login(req);
//
//        System.out.println(result.toString());
//
//
//    }
//
//    @Test
//    void loginFalse() throws Exception {
//        RegisterUserRequest info = new RegisterUserRequest();
//
//        info.setName("test");
//        info.setPassword("1234");
//        info.setUsername("objgogo");
//        info.setRoles(List.of(new String[]{"USER", "ADMIN"}));
//
//        RegisterUserResponse res = accountService.registerUser(info);
//
//        LoginRequest req = new LoginRequest();
//
//        req.setUsername("objgogo");
//        req.setPassword("12344");
//
//        JwtTokenResponse result = loginService.login(req);
//
//        System.out.println(result.toString());
//
//
//    }
//
//    @Test
//    void checkAuthority() throws Exception {
//
//        RegisterUserRequest info = new RegisterUserRequest();
//
//        info.setName("test");
//        info.setPassword("1234");
//        info.setUsername("objgogo");
//        info.setRoles(List.of(new String[]{"USER", "ADMIN"}));
//
//        RegisterUserResponse res = accountService.registerUser(info);
//
//        LoginRequest req = new LoginRequest();
//
//        req.setUsername("objgogo");
//        req.setPassword("1234");
//
//        JwtTokenResponse result = loginService.login(req);
//
//        System.out.println(result.toString());
//
//        String token = result.getAccessToken();
//
//        Authentication tmp = jwtTokenProvider.getAuthentication(token);
//        UserDetails user = (UserDetails) tmp.getPrincipal();
//
//        Collection<? extends GrantedAuthority> authorities =  user.getAuthorities();
//
//        boolean check = false;
//
//        UserType type = UserType.ADMIN;
//
//        for(GrantedAuthority a : authorities){
//            if(a.getAuthority().equals("ROLE_"+type.toString())){
//                check = true;
//                break;
//            }
//        }
//
//        System.out.println("결과 :: " + check);
//
//    }
//
//}
