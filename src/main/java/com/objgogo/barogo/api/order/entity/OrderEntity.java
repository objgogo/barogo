package com.objgogo.barogo.api.order.entity;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "order_mst")
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = AccountEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "order_from", columnDefinition = "VARCHAR(100) COMMENT '출발 주소'")
    private String orderFrom;

    @Column(name = "order_to", columnDefinition = "VARCHAR(100) COMMENT '도착지 주소'")
    private String orderTo;

    @Column(name = "city")
    private String city;

    @Column(name = "gu")
    private String gu;

    @Column(name = "dong")
    private String dong;

    @Column(name = "subject")
    private String subject;

    @Column(name = "demand")
    private String demand;

    @Column(name = "order_dt")
    private LocalDateTime orderDt;

    @OneToOne(mappedBy = "order", targetEntity = DeliveryEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private DeliveryEntity delivery;


    @OneToMany(mappedBy = "order" ,targetEntity = OrderStatusEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<OrderStatusEntity> orderStatusEntityList;

    @OneToMany(mappedBy = "order", targetEntity = OrderMenuEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<OrderMenuEntity> menuInfoList;
}
