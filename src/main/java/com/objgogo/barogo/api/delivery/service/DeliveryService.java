package com.objgogo.barogo.api.delivery.service;

import com.objgogo.barogo.api.delivery.vo.*;

import java.util.List;

public interface DeliveryService {

    //주문 배정
    TakeOrderResponse takeOrder(TakeOrderRequest req) throws Exception;

    //라이더가 접수한 주문 조회
    List<SearchDeliveryResponse> searchDelivery(SearchDeliveryRequest req);

    //배송 상태 변경(배달중, 완료, 실패, 취소 등)
    ChangeDeliveryStatusResponse changeDeliveryStatus(ChangeDeliveryStatusRequest req) throws Exception;


}
