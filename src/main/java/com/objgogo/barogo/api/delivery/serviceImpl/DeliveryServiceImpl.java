package com.objgogo.barogo.api.delivery.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryStatusEntity;
import com.objgogo.barogo.api.delivery.repository.DeliveryRepository;
import com.objgogo.barogo.api.delivery.repository.DeliveryStatusRepository;
import com.objgogo.barogo.api.delivery.service.DeliveryService;
import com.objgogo.barogo.api.delivery.vo.*;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.entity.OrderStatusEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.OrderStatus;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.exception.BarogoException;
import com.objgogo.barogo.common.util.UserUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.AbstractCollection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private OrderRepository orderRepository;
    private UserUtil userUtil;
    private DeliveryRepository deliveryRepository;
    private DeliveryStatusRepository deliveryStatusRepository;
    private OrderStatusRepository orderStatusRepository;

    public DeliveryServiceImpl(OrderRepository orderRepository, UserUtil userUtil, DeliveryRepository deliveryRepository, DeliveryStatusRepository deliveryStatusRepository, OrderStatusRepository orderStatusRepository) {
        this.orderRepository = orderRepository;
        this.userUtil = userUtil;
        this.deliveryRepository = deliveryRepository;
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public TakeOrderResponse takeOrder(TakeOrderRequest req) throws Exception {

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
                deliveryStatusEntity.setCreateDt(LocalDateTime.now());

                deliveryStatusEntity = deliveryStatusRepository.save(deliveryStatusEntity);

                OrderEntity order = checkOrder.get();

                OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
                orderStatusEntity.setOrder(order);
                orderStatusEntity.setStatus(OrderStatus.ACCEPT);
                orderStatusEntity.setCreateDt(LocalDateTime.now());

                orderStatusRepository.saveAndFlush(orderStatusEntity);

                TakeOrderResponse res = new TakeOrderResponse();
                res.setDeliveryId(deliveryEntity.getId());
                res.setOrderId(checkOrder.get().getId());

                return res;

            }else{
                throw new BarogoException("ERROR.DELIVERY.001");
            }
        } else{
            throw new BarogoException("ERROR.DELIVERY.002");
        }
    }

    @Override
    public List<SearchDeliveryResponse> searchDelivery(SearchDeliveryRequest req) {

        AccountEntity delivery = userUtil.me();

        List<DeliveryEntity> deliveryEntityList = deliveryRepository.findByAccountOrderByCreateDtDesc(delivery);

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<SearchDeliveryResponse>>() {}.getType();
        modelMapper.createTypeMap(DeliveryStatusEntity.class, DeliveryStatusInfo.class)
                .addMappings(mapper -> mapper.map(DeliveryStatusEntity::getId, DeliveryStatusInfo::setId))
                .addMappings(mapper -> mapper.map(DeliveryStatusEntity::getStatus, DeliveryStatusInfo::setStatus));
        modelMapper.createTypeMap(DeliveryEntity.class, SearchDeliveryResponse.class)
                .addMappings(mapper -> mapper.map(DeliveryEntity::getId, SearchDeliveryResponse::setId))
                .addMappings(mapping -> {
                    mapping.map(DeliveryEntity::getId, SearchDeliveryResponse::setId);
                    mapping.map(DeliveryEntity::getDeliveryStatus, SearchDeliveryResponse::setDeliveryStatusInfoList);
                });

        List<SearchDeliveryResponse> responseList = deliveryEntityList.stream()
                .map(a -> modelMapper.map(a, SearchDeliveryResponse.class))
                .collect(Collectors.toList());

        return responseList;
    }

    @Override
    public ChangeDeliveryStatusResponse changeDeliveryStatus(ChangeDeliveryStatusRequest req) throws Exception {

        Optional<DeliveryEntity> checkDelivery = deliveryRepository.findById(req.getDeliveryId());

        if(checkDelivery.isPresent()){

            DeliveryEntity delivery = checkDelivery.get();
            AccountEntity checkUser = userUtil.me();

            if(delivery.getAccount().getId() == checkUser.getId()){

                if(deliveryStatusRepository.findByDeliveryAndAndStatus(delivery,req.getDeliveryStatus()).isEmpty()) {
                    DeliveryStatusEntity deliveryStatusEntity = new DeliveryStatusEntity();
                    deliveryStatusEntity.setStatus(req.getDeliveryStatus());
                    deliveryStatusEntity.setDelivery(delivery);
                    deliveryStatusEntity.setCreateDt(LocalDateTime.now());

                    deliveryStatusEntity = deliveryStatusRepository.save(deliveryStatusEntity);

                    ChangeDeliveryStatusResponse res = new ChangeDeliveryStatusResponse();
                    res.setDeliveryId(delivery.getId());
                    res.setStatus(deliveryStatusEntity.getStatus().toString());

                    return res;
                } else {
                    throw new BarogoException("ERROR.DELIVERY.003");
                }

            } else {
                throw new BarogoException("ERROR.DELIVERY.004");
            }
        } else {
            throw new BarogoException("ERROR.DELIVERY.005");
        }



    }
}
