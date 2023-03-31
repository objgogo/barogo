package com.objgogo.barogo.api.order.service;

import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;


public interface OrderService {

    RegisterOrderResponse registerOrder(RegisterOrderRequest req);

}
