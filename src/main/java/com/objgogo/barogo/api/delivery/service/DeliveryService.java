package com.objgogo.barogo.api.delivery.service;

import com.objgogo.barogo.api.delivery.vo.TakeOrderRequest;

public interface DeliveryService {

    //주문 배정
    void takeOrder(TakeOrderRequest req) throws Exception;

    //배송 상태 변경(배달중, 완료, 실패, 취소 등)


}
