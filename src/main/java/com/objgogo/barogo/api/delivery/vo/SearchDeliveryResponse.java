package com.objgogo.barogo.api.delivery.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SearchDeliveryResponse {

    private Long id;

    private Long orderId;

    private LocalDateTime createDt;

    private List<DeliveryStatusInfo> deliveryStatusInfoList;


}
