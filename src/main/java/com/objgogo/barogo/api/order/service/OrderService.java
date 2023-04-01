package com.objgogo.barogo.api.order.service;

import com.objgogo.barogo.api.order.vo.OrderInfo;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderService {

    RegisterOrderResponse registerOrder(RegisterOrderRequest req) throws Exception;

    List<OrderInfo> getOrderList(String si, String gu, String dong, LocalDateTime time);

}
