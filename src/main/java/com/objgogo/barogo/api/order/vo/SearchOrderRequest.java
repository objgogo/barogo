package com.objgogo.barogo.api.order.vo;


import com.objgogo.barogo.common.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SearchOrderRequest {

    @ApiModelProperty(value = "출발주소 시")
    private String city;

    @ApiModelProperty(value = "출발주소 구")
    private String gu;

    @ApiModelProperty(value = "출발주소 동")
    private String dong;


    @ApiModelProperty(value = "검색 startDt")
    private LocalDate startDt;

    @ApiModelProperty(value = "검색 endDt")
    private LocalDate endDt;


    @ApiModelProperty(value = "status")
    private OrderStatus status;


    @ApiModelProperty(value = "pageNum", required = true)
    private Integer pageNum;


    @ApiModelProperty(value = "pageSize", required = true)
    private Integer pageSize;
}
