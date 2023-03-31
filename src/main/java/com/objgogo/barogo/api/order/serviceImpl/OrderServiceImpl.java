package com.objgogo.barogo.api.order.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;
import com.objgogo.barogo.common.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
    private UserUtil userUtil;

    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserUtil userUtil) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userUtil = userUtil;
    }

    @Override
    public RegisterOrderResponse registerOrder(RegisterOrderRequest req) {


        AccountEntity user = userUtil.me();
        ModelMapper mapper = new ModelMapper();
        OrderEntity orderEntity = mapper.map(req,OrderEntity.class);
        orderEntity.setAccount(user);


        orderEntity = orderRepository.save(orderEntity);



        return mapper.map(orderEntity,RegisterOrderResponse.class);
    }
}
