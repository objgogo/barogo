package com.objgogo.barogo.api.account.vo;

import com.objgogo.barogo.common.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegisterUserResponse {

    @ApiModelProperty(value = "계정 식별자")
    private Long id;

    @ApiModelProperty(value = "이름")
    private String name;

    @ApiModelProperty(value = "아이디")
    private String username;

    @ApiModelProperty(value = "계정 타입 (ADMIN,DELIVERY,USER)")
    private List<String> roles;

    @ApiModelProperty(value = "생성 일")
    private LocalDateTime createDt;

}
