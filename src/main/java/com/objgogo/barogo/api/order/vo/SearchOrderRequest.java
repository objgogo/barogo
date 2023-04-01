package com.objgogo.barogo.api.order.vo;


import com.objgogo.barogo.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchOrderRequest {

    private String city;

    private String gu;

    private String dong;

    private LocalDateTime time;

    private OrderStatus status;
}
