package com.objgogo.barogo.api.order.service;

import com.objgogo.barogo.api.delivery.vo.SearchDeliveryRequest;
import com.objgogo.barogo.api.order.vo.*;

import java.util.List;


public interface OrderService {

    /**
     * 사용자 주문 등록
     * @param req
     * @return
     * @throws Exception
     */
    RegisterOrderResponse registerOrder(RegisterOrderRequest req) throws Exception;

    /**
     * 사용자 주문 검색 조회
     * @param req
     * @return
     */
    List<OrderInfo> getOrderList(SearchOrderRequest req);

    /**
     * 라이더 배달 검색 조회
     * @param req
     * @return
     */
    List<OrderInfo> getDeliveryOrderList(SearchDeliveryRequest req);

    ChangeOrderToResponse changeOrderTo(ChangeOrderToRequest req) throws Exception;

}
