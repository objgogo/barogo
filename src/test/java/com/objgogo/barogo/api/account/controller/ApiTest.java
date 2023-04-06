package com.objgogo.barogo.api.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.api.account.service.AccountService;
import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.api.login.service.LoginService;
import com.objgogo.barogo.api.login.vo.LoginRequest;
import com.objgogo.barogo.api.order.vo.MenuInfo;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.SearchOrderRequest;
import com.objgogo.barogo.common.OrderStatus;
import com.objgogo.barogo.common.provider.JwtTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ApiTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoginService loginService;


    @Test
    @Order(1)
    @DisplayName("사용자 가입 테스트 - 정상적인 Request Parameter")
    public void registerUserOK() throws Exception {
        RegisterUserRequest req = new RegisterUserRequest();
        req.setRoles(List.of(new String[]{"USER"}));
        req.setName("사용자");
        req.setUsername("objgogo");
        req.setPassword("asdf1234ASDF!");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/api/account")
                                .content(jsonReq)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

    }

    @Test
    @Order(2)
    @DisplayName("배달원 가입 테스트 - 정상적인 Request Parameter")
    public void registerDeliveryOK() throws Exception {
        RegisterUserRequest req = new RegisterUserRequest();
        req.setRoles(List.of(new String[]{"DELIVERY"}));
        req.setName("라이더");
        req.setUsername("objgogo1");
        req.setPassword("asdf1234ASDF!");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);
        mockMvc
                .perform(
                        post("/api/account")
                                .content(jsonReq)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

    }

    @Test
    @Order(3)
    @DisplayName("로그인 테스트 - 사용자")
    public void loginUser() throws Exception {

        registerUserOK();

        LoginRequest req = new LoginRequest();
        req.setPassword("asdf1234ASDF!");
        req.setUsername("objgogo");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);

        mockMvc
                .perform(
                        post("/api/login")
                                .content(jsonReq)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(4)
    @DisplayName("로그인 테스트 - 라이더")
    public void loginDelivery() throws Exception {

        registerUserOK();

        LoginRequest req = new LoginRequest();
        req.setPassword("asdf1234ASDF!");
        req.setUsername("objgogo1");

        ObjectMapper mapper = new ObjectMapper();
        String jsonReq = mapper.writeValueAsString(req);

        mockMvc
                .perform(
                        post("/api/login")
                                .content(jsonReq)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(5)
    @DisplayName("주문 등록 - 사용자")
    public void registerOrderByUser() throws Exception {

        registerUserOK();

        LoginRequest req = new LoginRequest();
        req.setPassword("asdf1234ASDF!");
        req.setUsername("objgogo1");

        JwtTokenResponse res = loginService.login(req);

        RegisterOrderRequest orderReq = new RegisterOrderRequest();
        orderReq.setOrderDt(LocalDateTime.now());
        orderReq.setOrderTo("서울 강동구 길동");
        orderReq.setOrderTo("서울 강동구 명일동");
        orderReq.setCity("서울");
        orderReq.setGu("강동구");
        orderReq.setDong("길동");
        orderReq.setSubject("배달 품목");
        orderReq.setDemand("안전 유의하며 조심히 배달 부탁");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonReq = mapper.writeValueAsString(orderReq);

        mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq)
                        .header("Authorization",res.getAccessToken())
        ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(6)
    @DisplayName("주문 등록 - 라이더 - 오류 예상")
    public void registerOrderByDelivery() throws Exception {

        registerDeliveryOK();

        LoginRequest req = new LoginRequest();
        req.setPassword("asdf1234ASDF!");
        req.setUsername("objgogo1");

        JwtTokenResponse res = loginService.login(req);

        RegisterOrderRequest orderReq = new RegisterOrderRequest();
        orderReq.setOrderDt(LocalDateTime.now());
        orderReq.setOrderTo("서울 강동구 길동");
        orderReq.setOrderTo("서울 강동구 명일동");
        orderReq.setCity("서울");
        orderReq.setGu("강동구");
        orderReq.setDong("길동");
        orderReq.setSubject("배달 품목");
        orderReq.setDemand("안전 유의하며 조심히 배달 부탁");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonReq = mapper.writeValueAsString(orderReq);

        mockMvc.perform(
                post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq)
                        .header("Authorization",res.getAccessToken())
        ).andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("사용자 내가 등록한 주문 조회")
    public void searchUserOrder() throws Exception {
        registerUserOK();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("asdf1234ASDF!");
        loginRequest.setUsername("objgogo");

        JwtTokenResponse res = loginService.login(loginRequest);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        for(int i=0;i<21;i++){

            RegisterOrderRequest orderReq = new RegisterOrderRequest();
            orderReq.setOrderDt(LocalDateTime.now());
            orderReq.setOrderTo("서울 강동구 길동 " + i+"번지");
            orderReq.setOrderTo("서울 강동구 명일동 " + i + "번지");
            orderReq.setCity("서울");
            orderReq.setGu("강동구");
            orderReq.setDong("길동");
            orderReq.setSubject("배달 품목");
            orderReq.setDemand("안전 유의하며 조심히 배달 부탁");
            List<MenuInfo> menuInfoList = new LinkedList<>();

            for(int j=0;j<i+1;j++){
                MenuInfo menu = new MenuInfo();
                menu.setSubject("떡볶이");
                menu.setAmount(j+1);

                menuInfoList.add(menu);
            }
            orderReq.setMenuInfoList(menuInfoList);


            String jsonReq = mapper.writeValueAsString(orderReq);

            mockMvc.perform(
                    post("/api/order")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonReq)
                            .header("Authorization",res.getAccessToken())
            ).andDo(MockMvcResultHandlers.print());
        }

        SearchOrderRequest req = new SearchOrderRequest();
        req.setStatus(OrderStatus.WAIT);
        req.setPageNum(1);
        req.setPageSize(10);

        MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
        param.add("status",OrderStatus.WAIT.toString());
        param.add("pageNum","1");
        param.add("pageSize","10");

        mockMvc.perform(
                get("/api/order/list")
                .param("status","WAIT")
                .param("pageNum","1")
                .param("pageSize","10")

                .header("Authorization",res.getAccessToken())
        ).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print());

    }


}