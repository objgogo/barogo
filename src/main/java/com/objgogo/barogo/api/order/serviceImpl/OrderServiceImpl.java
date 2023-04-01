package com.objgogo.barogo.api.order.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.OrderInfo;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    EntityManager entityManager;

    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
    private UserUtil userUtil;



    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserUtil userUtil) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userUtil = userUtil;
    }

    @Override
    public RegisterOrderResponse registerOrder(RegisterOrderRequest req) throws Exception {

        boolean isUser = userUtil.checkAuthority(UserType.USER);
        if(isUser){
            AccountEntity user = userUtil.me();
            ModelMapper mapper = new ModelMapper();
            OrderEntity orderEntity = mapper.map(req,OrderEntity.class);
            orderEntity.setAccount(user);
            orderEntity = orderRepository.save(orderEntity);

            return mapper.map(orderEntity,RegisterOrderResponse.class);
        } else{
            throw new Exception("사용자만 주문을 등록할 수 있습니다.");
        }

    }

    @Override
    public List<OrderInfo> getOrderList(String si, String gu, String dong, LocalDateTime time) {




        return null;
    }
}
