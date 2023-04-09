package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RegisterOrderRequest {

    @ApiModelProperty(value = "주문 요청한 주소")
    private String orderTo;

    @ApiModelProperty(value = "배송지 주소")
    private String orderFrom;

    @ApiModelProperty(value = "배송지 시")
    private String city;

    @ApiModelProperty(value = "배송지 구")
    private String gu;

    @ApiModelProperty(value = "배송지 동")
    private String dong;

    @ApiModelProperty(value = "제목")
    private String subject;

    @ApiModelProperty(value = "요구 사항")
    private String demand;

    @ApiModelProperty(value = "메뉴 목록")
    private List<MenuInfo> menuInfoList;

    @ApiModelProperty(value = "주문 요청 시간")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDt;

}
