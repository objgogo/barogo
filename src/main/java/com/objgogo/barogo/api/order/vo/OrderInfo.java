package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderInfo {

    @ApiModelProperty(value = "주문 식별자")
    private Long id;

    @ApiModelProperty(value = "출발지 주소")
    private String orderTo;

    @ApiModelProperty(value = "도착지 주소")
    private String orderFrom;

    @ApiModelProperty(value = "제목")
    private String subject;

    @ApiModelProperty(value = "배송자 요구사항")
    private String demand;

    @ApiModelProperty(value = "주문 등록 시간")
    private LocalDateTime orderDt;

    @ApiModelProperty(value = "배달 최종 상태")
    private String deliveryStatus;

    @ApiModelProperty(value = "주문 최종 상태")
    private String orderStatus;

    @ApiModelProperty(value = "메뉴 목록")
    private List<MenuInfo> menuInfoList;


}
