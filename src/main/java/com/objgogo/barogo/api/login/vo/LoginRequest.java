package com.objgogo.barogo.api.login.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {

    @ApiModelProperty(value = "사용자 아이디")
    private String username;

    @ApiModelProperty(value = "사용자 비밀번호")
    private String password;
}
