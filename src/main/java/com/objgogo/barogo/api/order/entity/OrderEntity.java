package com.objgogo.barogo.api.order.entity;

import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "order_from")
    private String orderFrom;

    @Column(name = "order_to")
    private String orderTo;

    @Column(name = "order_dt")
    private LocalDateTime orderDt;

    @OneToMany(mappedBy = "orderId" ,targetEntity = OrderStatusEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<OrderStatusEntity> orderStatusEntityList;
}
