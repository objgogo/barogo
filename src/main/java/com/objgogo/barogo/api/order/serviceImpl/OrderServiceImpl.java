package com.objgogo.barogo.api.order.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.QDeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.QDeliveryStatusEntity;
import com.objgogo.barogo.api.delivery.repository.DeliveryStatusRepository;
import com.objgogo.barogo.api.delivery.vo.SearchDeliveryRequest;
import com.objgogo.barogo.api.order.entity.*;
import com.objgogo.barogo.api.order.repository.OrderMenuRepository;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.*;
import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.OrderStatus;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.exception.BarogoException;
import com.objgogo.barogo.common.util.SearchUtil;
import com.objgogo.barogo.common.util.UserUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
    private UserUtil userUtil;
    private JPAQueryFactory queryFactory;
    private DeliveryStatusRepository deliveryStatusRepository;
    private OrderMenuRepository orderMenuRepository;



    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserUtil userUtil, JPAQueryFactory queryFactory, DeliveryStatusRepository deliveryStatusRepository, OrderMenuRepository orderMenuRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userUtil = userUtil;
        this.queryFactory = queryFactory;
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.orderMenuRepository = orderMenuRepository;

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


            OrderStatusEntity orderStatusEntity = new OrderStatusEntity();
            orderStatusEntity.setOrder(orderEntity);
            orderStatusEntity.setStatus(OrderStatus.WAIT);
            orderStatusEntity.setCreateDt(LocalDateTime.now());

            for(MenuInfo menu : req.getMenuInfoList()){
                OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
                orderMenuEntity.setOrder(orderEntity);
                orderMenuEntity.setAmount(menu.getAmount());
                orderMenuEntity.setSubject(menu.getSubject());

                orderMenuRepository.save(orderMenuEntity);
            }

            orderStatusRepository.saveAndFlush(orderStatusEntity);

            return mapper.map(orderEntity,RegisterOrderResponse.class);
        } else{
            throw new BarogoException("ERROR.ORDER.001");
        }

    }

    @Override
    public List<OrderInfo> getOrderList(SearchOrderRequest req) {

        ModelMapper mapper = new ModelMapper();

        int offset = (req.getPageNum()-1) * req.getPageSize();

        QOrderEntity order = QOrderEntity.orderEntity;
        QOrderStatusEntity orderStatus = QOrderStatusEntity.orderStatusEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(req.getCity())) {
            builder.and(order.city.eq(req.getCity()));
        }

        if (StringUtils.hasText(req.getDong())) {
            builder.and(order.dong.eq(req.getDong()));
        }

        if( null != req.getStartDt() && null != req.getEndDt()){
            if(SearchUtil.isDateRangeValid(req.getStartDt(),req.getEndDt(),3L)){

                if(req.getStartDt() != null && null == req.getEndDt()){
                    LocalDateTime startDt = req.getStartDt().atStartOfDay();
                    LocalDateTime endDt = LocalDateTime.of(req.getStartDt().plusDays(3), LocalTime.MAX);

                    builder.and(order.orderDt.between(startDt,endDt));
                } else if(req.getEndDt() != null && null == req.getStartDt()){
                    LocalDateTime startDt = req.getEndDt().minusDays(3).atStartOfDay();
                    LocalDateTime endDt = LocalDateTime.of(req.getEndDt(), LocalTime.MAX);

                    builder.and(order.orderDt.between(startDt,endDt));
                }
            }
        } else {
            throw new BarogoException("ERROR.ORDER.004");
        }

        if (StringUtils.hasText(req.getGu())) {
            builder.and(order.gu.eq(req.getGu()));
        }

        if ( null != req.getStatus() && StringUtils.hasText(req.getStatus().toString())) {
            JPQLQuery<OrderStatus> subQuery = JPAExpressions
                    .select(orderStatus.status)
                    .from(orderStatus)
                    .where(orderStatus.order.id.eq(order.id))
                    .orderBy(orderStatus.createDt.desc())
                    .limit(1);

            builder.and(subQuery.eq(req.getStatus()));
        }

        List<OrderEntity> result = queryFactory.selectFrom(order)
                .where(builder)
                .offset(offset)
                .limit(req.getPageSize())
                .orderBy(order.orderDt.desc())
                .fetch();

        List<OrderInfo> res = new LinkedList<>();


        for(OrderEntity o : result){

            OrderInfo info = mapper.map(o, OrderInfo.class);

            if(null != o.getDelivery() && o.getDelivery().getDeliveryStatus().size() != 0){
                info.setDeliveryStatus(deliveryStatusRepository.getOrderDeliveryStatus(o, PageRequest.of(0,1)).get(0).getStatus().toString());
            } else {
                info.setDeliveryStatus("WAIT");
            }
            info.setOrderStatus(orderStatusRepository.findTop1ByOrderOrderByCreateDtDesc(o).getStatus().toString());

            res.add(info);
        }


        return res;
    }

    @Override
    public List<OrderInfo> getDeliveryOrderList(SearchDeliveryRequest req) {

        QDeliveryEntity delivery = QDeliveryEntity.deliveryEntity;
        QDeliveryStatusEntity deliveryStatus = QDeliveryStatusEntity.deliveryStatusEntity;

        int offset = (req.getPageNum()-1) * req.getPageSize();

        BooleanBuilder builder = new BooleanBuilder();

        if(null != req.getStartDt()){
            builder.and(delivery.createDt.goe(req.getStartDt()));
        }

        if(null != req.getStartDt()){
            builder.and(delivery.createDt.loe(req.getEndDt()));
        }

        if(null != req.getStatus()){
            JPQLQuery<DeliveryStatus> subQuery = JPAExpressions
                    .select(deliveryStatus.status)
                    .from(deliveryStatus)
                    .where(deliveryStatus.delivery.id.eq(delivery.id))
                    .orderBy(deliveryStatus.createDt.desc())
                    .limit(1);

            builder.and(subQuery.eq(req.getStatus()));
        }

        List<DeliveryEntity> deliveryEntityList = queryFactory.selectFrom(delivery)
                .where(builder)
                .orderBy(delivery.createDt.desc())
                .offset(offset)
                .limit(req.getPageSize()).fetch();

        ModelMapper mapper = new ModelMapper();
        List<OrderInfo> res = new LinkedList<>();

        for(DeliveryEntity d : deliveryEntityList){
            OrderEntity order = d.getOrder();
            res.add(mapper.map(order,OrderInfo.class));
        }

        return res;
    }

    @Override
    public ChangeOrderToResponse changeOrderTo(ChangeOrderToRequest req) throws Exception {

        AccountEntity user = userUtil.me();

        //isMyOrder?
        Optional<OrderEntity> isMyOrder = orderRepository.findByIdAndAccount(req.getOrderId(),user);

        if(isMyOrder.isPresent()){

            OrderEntity order = isMyOrder.get();
            OrderStatusEntity orderStatus = orderStatusRepository.findTop1ByOrderOrderByCreateDtDesc(order);

            // 주문이 대기중일떄 만 수정이 가능 하도록 조건을
            if(orderStatus.getStatus().equals(OrderStatus.WAIT)){

                order.setOrderTo(req.getOrderTo());

                order = orderRepository.save(order);

                OrderStatusEntity newOrderStatus = new OrderStatusEntity();
                newOrderStatus.setOrder(order);
                newOrderStatus.setStatus(OrderStatus.MODIFY);
                newOrderStatus.setCreateDt(LocalDateTime.now());

                orderStatusRepository.save(newOrderStatus);

                ChangeOrderToResponse res = new ChangeOrderToResponse();
                res.setOrderId(order.getId());

                return res;
            } else {
                throw new BarogoException("ERROR.ORDER.002");
            }
        } else {
            throw new BarogoException("ERROR.ORDER.003");
        }
    }
}
