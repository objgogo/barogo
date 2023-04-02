package com.objgogo.barogo.api.delivery.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryStatusEntity;
import com.objgogo.barogo.api.delivery.repository.DeliveryRepository;
import com.objgogo.barogo.api.delivery.repository.DeliveryStatusRepository;
import com.objgogo.barogo.api.delivery.service.DeliveryService;
import com.objgogo.barogo.api.delivery.vo.TakeOrderRequest;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.util.UserUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private OrderRepository orderRepository;
    private UserUtil userUtil;
    private DeliveryRepository deliveryRepository;
    private DeliveryStatusRepository deliveryStatusRepository;

    public DeliveryServiceImpl(OrderRepository orderRepository, UserUtil userUtil, DeliveryRepository deliveryRepository, DeliveryStatusRepository deliveryStatusRepository) {
        this.orderRepository = orderRepository;
        this.userUtil = userUtil;
        this.deliveryRepository = deliveryRepository;
        this.deliveryStatusRepository = deliveryStatusRepository;
    }

    @Override
    public void takeOrder(TakeOrderRequest req) throws Exception {

        Boolean isDelivery = userUtil.checkAuthority(UserType.DELIVERY);
        if(isDelivery){
            AccountEntity deliveryUser = userUtil.me();

            Optional<OrderEntity> checkOrder = orderRepository.findById(req.getOrderId());
            if(checkOrder.isPresent()){
                DeliveryEntity deliveryEntity = new DeliveryEntity();
                deliveryEntity.setOrder(checkOrder.get());
                deliveryEntity.setAccount(deliveryUser);
                deliveryEntity.setCreateDt(LocalDateTime.now());

                deliveryEntity = deliveryRepository.save(deliveryEntity);

                DeliveryStatusEntity deliveryStatusEntity = new DeliveryStatusEntity();

                deliveryStatusEntity.setDelivery(deliveryEntity);
                deliveryStatusEntity.setStatus(DeliveryStatus.RECEIPT);
                deliveryStatusEntity.setCrateDt(LocalDateTime.now());

                deliveryStatusRepository.save(deliveryStatusEntity);

            }else{
                throw new Exception("존재하지 않는 Order 입니다.");
            }
        } else{
            throw new Exception("Delivery만 주문을 받을 수 있습니다.");
        }

    }


}
