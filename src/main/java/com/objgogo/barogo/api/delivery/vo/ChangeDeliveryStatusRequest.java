package com.objgogo.barogo.api.delivery.vo;

import com.objgogo.barogo.common.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDeliveryStatusRequest {

    private Long deliveryId;

    private DeliveryStatus deliveryStatus;

}
