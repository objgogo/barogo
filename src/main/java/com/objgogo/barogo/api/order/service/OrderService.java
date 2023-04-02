package com.objgogo.barogo.api.order.service;

import com.objgogo.barogo.api.delivery.vo.SearchDeliveryRequest;
import com.objgogo.barogo.api.order.vo.OrderInfo;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;
import com.objgogo.barogo.api.order.vo.SearchOrderRequest;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderService {

    RegisterOrderResponse registerOrder(RegisterOrderRequest req) throws Exception;

    List<OrderInfo> getOrderList(SearchOrderRequest req);

    List<OrderInfo> getDeliveryOrderList(SearchDeliveryRequest req);

}
