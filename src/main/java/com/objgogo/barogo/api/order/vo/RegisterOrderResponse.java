package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterOrderResponse {

    @ApiModelProperty(value = "주문 식별자")
    private Long id;

    @ApiModelProperty(value = "주문 요청한 주소")
    private String orderTo;

    @ApiModelProperty(value = "배송지 주소")
    private String orderFrom;

    @ApiModelProperty(value = "제목")
    private String subject;

    @ApiModelProperty(value = "요구 사항")
    private String demand;

    @ApiModelProperty(value = "주문 요청 시간")
    private LocalDateTime orderDt;

}
