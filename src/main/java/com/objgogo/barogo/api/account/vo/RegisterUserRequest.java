package com.objgogo.barogo.api.account.vo;

import com.objgogo.barogo.common.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
public class RegisterUserRequest {

    @ApiModelProperty(value = "이름")
    @NotEmpty(message = "이름을 입력해 주세요")
    private String name;

    @ApiModelProperty(value = "아이디")
    @NotEmpty(message = "아이디를 입력해 주세요")
    private String username;

    @ApiModelProperty(value = "비밀번호, 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={};':\",./?])(?=\\S+$).{12,}$",
            message = "비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상이어야 합니다.")
    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;

    @ApiModelProperty(value = "계정 타입 [ADMIN,DELIVERY,USER]")
    @NotEmpty(message = "계정 타입을 입력해 주세요")
    private List<String> roles;

}
