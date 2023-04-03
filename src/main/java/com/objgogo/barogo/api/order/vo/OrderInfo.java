package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderInfo {

    @ApiModelProperty(name = "주문 식별자")
    private Long id;

    @ApiModelProperty(name = "출발지 주소")
    private String orderTo;

    @ApiModelProperty(name = "도착지 주소")
    private String orderFrom;

    @ApiModelProperty(name = "제목")
    private String subject;

    @ApiModelProperty(name = "배송자 요구사항")
    private String demand;

    @ApiModelProperty(name = "주문 등록 시간")
    private LocalDateTime orderDt;

    @ApiModelProperty(name = "배달 최종 상태")
    private String deliveryStatus;

    @ApiModelProperty(name = "주문 최종 상태")
    private String orderStatus;

}
