package com.objgogo.barogo;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.account.repository.AccountRepository;
import com.objgogo.barogo.api.account.service.AccountService;
import com.objgogo.barogo.api.account.vo.RegisterUserRequest;
import com.objgogo.barogo.api.account.vo.RegisterUserResponse;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryStatusEntity;
import com.objgogo.barogo.api.delivery.repository.DeliveryRepository;
import com.objgogo.barogo.api.delivery.repository.DeliveryStatusRepository;
import com.objgogo.barogo.api.delivery.vo.DeliveryStatusInfo;
import com.objgogo.barogo.api.delivery.vo.SearchDeliveryResponse;
import com.objgogo.barogo.api.login.service.LoginService;
import com.objgogo.barogo.api.login.vo.LoginRequest;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.api.order.entity.OrderStatusEntity;
import com.objgogo.barogo.api.order.entity.QOrderEntity;
import com.objgogo.barogo.api.order.entity.QOrderStatusEntity;
import com.objgogo.barogo.api.order.repository.OrderRepository;
import com.objgogo.barogo.api.order.repository.OrderStatusRepository;
import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.SearchOrderRequest;
import com.objgogo.barogo.common.DeliveryStatus;
import com.objgogo.barogo.common.OrderStatus;
import com.objgogo.barogo.common.UserType;
import com.objgogo.barogo.common.provider.JwtTokenProvider;
import com.objgogo.barogo.common.provider.JwtTokenResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class BarogoApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Test
    void contextLoads() {
        System.out.println("test");
    }


    @Test
    void modelMapperTest(){

        AccountEntity entity = new AccountEntity();
        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.DELIVERY}));

        ModelMapper mapper = new ModelMapper();

        entity = mapper.map(info,AccountEntity.class);

        System.out.println(entity.toString());

    }

    @Test
    void saveAccount(){

        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.ADMIN}));

        RegisterUserResponse res = accountService.registerUser(info);

        System.out.println(res.toString());

    }

    @Test
    void loginTrue() throws Exception {
        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.DELIVERY}));

        RegisterUserResponse res = accountService.registerUser(info);

        LoginRequest req = new LoginRequest();

        req.setUsername("objgogo");
        req.setPassword("1234");

        JwtTokenResponse result = loginService.login(req);

        System.out.println(result.toString());


    }

    @Test
    void checkAuthority() throws Exception {

        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.DELIVERY}));

        RegisterUserResponse res = accountService.registerUser(info);

        LoginRequest req = new LoginRequest();

        req.setUsername("objgogo");
        req.setPassword("1234");

        JwtTokenResponse result = loginService.login(req);

        System.out.println(result.toString());

        String token = result.getAccessToken();

        Authentication tmp = jwtTokenProvider.getAuthentication(token);
        UserDetails user = (UserDetails) tmp.getPrincipal();

        Collection<? extends GrantedAuthority> authorities =  user.getAuthorities();

        boolean check = false;

        UserType type = UserType.ADMIN;

        for(GrantedAuthority a : authorities){
            if(a.getAuthority().equals("ROLE_"+type.toString())){
                check = true;
                break;
            }
        }

        System.out.println("결과 :: " + check);

    }

    @Test
    void querydslTest(){

        //회원가입
        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.DELIVERY}));

        RegisterUserResponse res = accountService.registerUser(info);

        System.out.println(res.toString());

        //주문 등록
        Optional<AccountEntity> account = accountRepository.findByUsername("objgogo");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAccount(account.get());
        orderEntity.setOrderDt(LocalDateTime.now());
        orderEntity.setOrderTo("서울특별시 강동구 명일동 123");
        orderEntity.setOrderFrom("서울특별시 강동구 길동 123");
        orderEntity.setCity("서울");
        orderEntity.setGu("강동구");
        orderEntity.setDong("명일동");
        orderEntity.setDemand("빨리빨리");
        orderEntity.setSubject("떡복이");

        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setAccount(account.get());
        orderEntity2.setOrderDt(LocalDateTime.now());
        orderEntity2.setOrderTo("서울특별시 강동구 명일동 123");
        orderEntity2.setOrderFrom("서울특별시 강동구 길동 123");
        orderEntity2.setCity("부산");
        orderEntity2.setGu("강동구");
        orderEntity2.setDong("명일동");
        orderEntity2.setDemand("빨리빨리");
        orderEntity2.setSubject("떡복이");

        OrderEntity orderEntity3 = new OrderEntity();
        orderEntity3.setAccount(account.get());
        orderEntity3.setOrderDt(LocalDateTime.now());
        orderEntity3.setOrderTo("서울특별시 강동구 명일동 123");
        orderEntity3.setOrderFrom("서울특별시 강동구 길동 123");
        orderEntity3.setCity("서울");
        orderEntity3.setGu("강동구");
        orderEntity3.setDong("명일동1");
        orderEntity3.setDemand("빨리빨리");
        orderEntity3.setSubject("떡복이");

        orderEntity = orderRepository.saveAndFlush(orderEntity);
        orderEntity2 = orderRepository.saveAndFlush(orderEntity2);
        orderEntity3 = orderRepository.saveAndFlush(orderEntity3);


        OrderStatusEntity osEntity = new OrderStatusEntity();
        osEntity.setStatus(OrderStatus.WAIT);
        osEntity.setOrder(orderEntity);
        osEntity.setCreateDt(LocalDateTime.now());

        OrderStatusEntity osEntity2 = new OrderStatusEntity();
        osEntity2.setStatus(OrderStatus.WAIT);
        osEntity2.setOrder(orderEntity2);
        osEntity2.setCreateDt(LocalDateTime.now());

        OrderStatusEntity osEntity3 = new OrderStatusEntity();
        osEntity3.setStatus(OrderStatus.WAIT);
        osEntity3.setOrder(orderEntity3);
        osEntity3.setCreateDt(LocalDateTime.now());

        orderStatusRepository.saveAndFlush(osEntity);
        orderStatusRepository.saveAndFlush(osEntity2);
        orderStatusRepository.saveAndFlush(osEntity3);



        String si = "서울";
        String gu = "강동구";
        String dong = null;
        LocalDateTime time = LocalDateTime.now();

        SearchOrderRequest req = new SearchOrderRequest();
        req.setCity("서울");
        req.setGu("강동구");
        req.setPageNum(1);
        req.setPageSize(10);
        req.setStatus(OrderStatus.WAIT);


        QOrderEntity qOrderEntity =QOrderEntity.orderEntity;

        BooleanExpression booleanExpression = null;

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

        if (null != req.getStatus()) {
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
                .fetch();

        for(OrderEntity o : result){
            System.out.println("결과 >> " + o.getId());
        }
    }

    @Test
    void orderAccept(){
        //사용자 회원 가입
        //사용자 주문 등록
        querydslTest();

        //라이더 회원 가입
        RegisterUserRequest info = new RegisterUserRequest();

        info.setName("test");
        info.setPassword("1234");
        info.setUsername("objgogo1");
        info.setRoles(List.of(new UserType[]{UserType.USER,UserType.DELIVERY}));

        RegisterUserResponse res = accountService.registerUser(info);

        //라이더 정보
        Optional<AccountEntity> delivery = accountRepository.findByUsername("objgogo1");

        //라이더 주문 선택
        //주문 조회

        SearchOrderRequest req = new SearchOrderRequest();
        req.setCity("서울");
        req.setGu("강동구");
        req.setPageNum(1);
        req.setPageSize(10);
        req.setStatus(OrderStatus.WAIT);

        QOrderEntity qOrderEntity =QOrderEntity.orderEntity;

        BooleanExpression booleanExpression = null;

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

        if (null != req.getStatus()) {
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
                .fetch();

        for(OrderEntity o : result){

            DeliveryEntity deliveryEntity = new DeliveryEntity();
            deliveryEntity.setOrder(o);
            deliveryEntity.setAccount(delivery.get());
            deliveryEntity.setCreateDt(LocalDateTime.now());

            deliveryEntity = deliveryRepository.save(deliveryEntity);

            DeliveryStatusEntity deliveryStatusEntity = new DeliveryStatusEntity();
            deliveryStatusEntity.setDelivery(deliveryEntity);
            deliveryStatusEntity.setStatus(DeliveryStatus.RECEIPT);
            deliveryStatusEntity.setCreateDt(LocalDateTime.now());

            deliveryStatusRepository.save(deliveryStatusEntity);
        }

        //결과 확인

        List<DeliveryEntity> reDeliveryList = deliveryRepository.findAll();

        for(DeliveryEntity d : reDeliveryList){


            System.out.println("결과 배달 식별자>> " + d.getId());
            System.out.println("결과 라이더 식별자>> " + d.getAccount().getId());
            System.out.println("결과 주문 식별자>> " + d.getOrder().getId());
            System.out.println("결과 배달 상태 식별자>> " + deliveryStatusRepository.getOrderDeliveryStatus(d.getOrder(), PageRequest.of(0,1)).get(0).getStatus().toString() );
            System.out.println("--------------");


        }

    }

    @Test
    void modelMapperTest2(){

        DeliveryEntity a = new DeliveryEntity();
        a.setId(1L);
        a.setCreateDt(LocalDateTime.now());



        DeliveryStatusEntity b1 = new DeliveryStatusEntity();
        b1.setDelivery(a);
        b1.setStatus(DeliveryStatus.RECEIPT);

        DeliveryStatusEntity b2 = new DeliveryStatusEntity();
        b2.setDelivery(a);
        b2.setStatus(DeliveryStatus.START);

        List<DeliveryStatusEntity> b = new LinkedList<>();
        b.add(b1);
        b.add(b2);

        a.setDeliveryStatus(b);

        List<DeliveryEntity> alist = new LinkedList<>();
        alist.add(a);

        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<SearchDeliveryResponse>>() {}.getType();
        modelMapper.createTypeMap(DeliveryStatusEntity.class, DeliveryStatusInfo.class)
                .addMappings(mapper -> mapper.map(DeliveryStatusEntity::getId, DeliveryStatusInfo::setId))
                .addMappings(mapper -> mapper.map(DeliveryStatusEntity::getStatus, DeliveryStatusInfo::setStatus));
        modelMapper.createTypeMap(DeliveryEntity.class, SearchDeliveryResponse.class)
                .addMappings(mapper -> mapper.map(DeliveryEntity::getId, SearchDeliveryResponse::setId))
                .addMappings(mapping -> {
                    mapping.map(DeliveryEntity::getCreateDt, SearchDeliveryResponse::setCreateDt);
                    mapping.map(DeliveryEntity::getDeliveryStatus, SearchDeliveryResponse::setDeliveryStatusInfoList);
                });



        List<SearchDeliveryResponse> responseList = alist.stream()
                .map(v -> modelMapper.map(v, SearchDeliveryResponse.class))
                .collect(Collectors.toList());

        for (SearchDeliveryResponse t: responseList) {
            System.out.println(t.getId());
            System.out.println(t.getDeliveryStatusInfoList().get(0).getStatus().toString());
            System.out.println(t.getDeliveryStatusInfoList().get(1).getStatus().toString());
        }

    }

    @Test
    void passwordEncoderTest(){

        String password = "asdf1234ASDF!";

        String tmp = passwordEncoder.encode(password);

        System.out.println(tmp);
    }

}
