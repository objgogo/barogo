package com.objgogo.barogo.api.order.entity;


import com.objgogo.barogo.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status")
@Getter
@Setter
public class OrderStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "create_dt")
    private LocalDateTime createDt;

    @ManyToOne(targetEntity = OrderEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", referencedColumnName="id")
    private OrderEntity orderId;


}
