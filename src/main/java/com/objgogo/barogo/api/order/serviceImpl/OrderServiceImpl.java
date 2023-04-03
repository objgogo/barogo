package com.objgogo.barogo.api.order.serviceImpl;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.QDeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.QDeliveryStatusEntity;
import com.objgogo.barogo.api.delivery.repository.DeliveryStatusRepository;
import com.objgogo.barogo.api.delivery.vo.SearchDeliveryRequest;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.entity.OrderStatusEntity;
import com.objgogo.barogo.api.order.entity.QOrderEntity;
import com.objgogo.barogo.api.order.entity.QOrderStatusEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.OrderInfo;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;
import com.objgogo.barogo.api.order.vo.SearchOrderRequest;
import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.OrderStatus;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.util.UserUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderStatusRepository orderStatusRepository;
    private UserUtil userUtil;
    private JPAQueryFactory queryFactory;
    private DeliveryStatusRepository deliveryStatusRepository;


    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusRepository orderStatusRepository, UserUtil userUtil, JPAQueryFactory queryFactory, DeliveryStatusRepository deliveryStatusRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userUtil = userUtil;
        this.queryFactory = queryFactory;
        this.deliveryStatusRepository = deliveryStatusRepository;
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

            orderStatusRepository.saveAndFlush(orderStatusEntity);

            return mapper.map(orderEntity,RegisterOrderResponse.class);
        } else{
            throw new Exception("사용자만 주문을 등록할 수 있습니다.");
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

            if(o.getDelivery().getDeliveryStatus().size() != 0){
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
}
