package com.objgogo.barogo.api.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChangeOrderToRequest {

    private Long orderId;

    @ApiModelProperty(value = "도착지 주소")
    private String orderTo;


}
