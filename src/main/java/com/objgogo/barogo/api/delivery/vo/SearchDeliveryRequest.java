package com.objgogo.barogo.api.delivery.vo;


import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchDeliveryRequest {

    private LocalDateTime startDt;

    private LocalDateTime endDt;

    private DeliveryStatus status;

    @ApiModelProperty(name = "페이지 번호", required = true)
    private Integer pageNum;

    @ApiModelProperty(name = "페이지 사이즈", required = true)
    private Integer pageSize;
}
