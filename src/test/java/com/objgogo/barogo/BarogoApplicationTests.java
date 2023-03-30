package com.objgogo.barogo;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.common.UserType;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

class BarogoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("test");
    }

    @Test
    void modelMapperTest(){
        AccountEntity entity = new AccountEntity();

        RegisterUserRequest info = new RegisterUserRequest();

        info.setCreateDt(LocalDateTime.now());
        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setUserType(UserType.USER);

        ModelMapper mapper = new ModelMapper();

        entity = mapper.map(info,AccountEntity.class);
        System.out.println(entity.toString());

    }

}
