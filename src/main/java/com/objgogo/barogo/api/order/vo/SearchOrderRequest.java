package com.objgogo.barogo.api.order.vo;


import com.objgogo.barogo.common.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchOrderRequest {

    @ApiModelProperty(name = "주소 시")
    private String city;

    @ApiModelProperty(name = "주소 구")
    private String gu;

    @ApiModelProperty(name = "주소 동")
    private String dong;


    @ApiModelProperty(name = "시간")
    private LocalDateTime time;


    @ApiModelProperty(name = "주문 상태")
    private OrderStatus status;


    @ApiModelProperty(name = "페이지 번호", required = true)
    private Integer pageNum;


    @ApiModelProperty(name = "페이지 사이즈", required = true)
    private Integer pageSize;
}
