package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterOrderRequest {

    @ApiModelProperty(name = "주문 요청한 주소")
    private String orderTo;

    @ApiModelProperty(name = "배송지 주소")
    private String orderFrom;

    @ApiModelProperty(name = "배송지 시")
    private String city;

    @ApiModelProperty(name = "배송지 구")
    private String gu;

    @ApiModelProperty(name = "배송지 동")
    private String dong;

    @ApiModelProperty(name = "제목")
    private String subject;

    @ApiModelProperty(name = "요구 사항")
    private String demand;

    @ApiModelProperty(name = "주문 요청 시간")
    private LocalDateTime orderDt;

}
