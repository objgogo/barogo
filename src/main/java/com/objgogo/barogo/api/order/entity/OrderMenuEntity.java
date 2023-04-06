package com.objgogo.barogo.api.order.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_menu")
@Getter
@Setter
public class OrderMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = OrderEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", referencedColumnName="id")
    private OrderEntity order;

    @Column(name = "subject")
    private String subject;

    @Column(name = "amount")
    private Integer amount;




}
