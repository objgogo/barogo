package com.objgogo.barogo.api.order.vo;


import com.objgogo.barogo.common.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchOrderRequest {

    @ApiModelProperty(value = "city")
    private String city;

    @ApiModelProperty(value = "gu")
    private String gu;

    @ApiModelProperty(value = "dong")
    private String dong;


    @ApiModelProperty(value = "time")
    private LocalDateTime time;


    @ApiModelProperty(value = "status")
    private OrderStatus status;


    @ApiModelProperty(value = "pageNum", required = true)
    private Integer pageNum;


    @ApiModelProperty(value = "pageSize", required = true)
    private Integer pageSize;
}
