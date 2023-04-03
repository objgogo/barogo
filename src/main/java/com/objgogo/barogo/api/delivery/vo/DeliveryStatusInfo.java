package com.objgogo.barogo.api.delivery.vo;

import com.objgogo.barogo.common.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryStatusInfo {

    private Long id;

    private DeliveryStatus status;

    private LocalDateTime createDt;

}
