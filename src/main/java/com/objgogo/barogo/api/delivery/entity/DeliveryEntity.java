package com.objgogo.barogo.api.delivery.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_mst")
@Getter
@Setter
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_from")
    private String from;

    @Column(name = "order_to")
    private String to;

    @Column(name = "order_dt")
    private LocalDateTime orderDt;



}
