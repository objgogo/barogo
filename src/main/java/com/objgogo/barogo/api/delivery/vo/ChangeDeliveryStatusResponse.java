package com.objgogo.barogo.api.delivery.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDeliveryStatusResponse {

    private Long deliveryId;

    private String status;
}
