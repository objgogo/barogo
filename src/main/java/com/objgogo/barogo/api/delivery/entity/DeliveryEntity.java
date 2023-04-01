package com.objgogo.barogo.api.delivery.entity;

import com.objgogo.barogo.api.account.entity.AccountEntity;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "delivery_mst")
@Getter
@Setter
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = OrderEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToOne(targetEntity = AccountEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @OneToMany(mappedBy = "delivery", targetEntity = DeliveryStatusEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<DeliveryStatusEntity> deliveryStatus;

    @Column(name = "create_dt")
    private LocalDateTime createDt;



}
