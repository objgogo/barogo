package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuInfo {

    @ApiModelProperty(value = "메뉴 이름")
    private String subject;

    @ApiModelProperty(value = "수량")
    private Integer amount;
}
