package com.objgogo.barogo.api.order.entity;

import com.objgogo.barogo.api.account.entity.AccountEntity;
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


    @ManyToOne(targetEntity = AccountEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @Column(name = "order_from")
    private String orderFrom;

    @Column(name = "order_to")
    private String orderTo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "demand")
    private String demand;

    @Column(name = "order_dt")
    private LocalDateTime orderDt;

    @OneToMany(mappedBy = "order" ,targetEntity = OrderStatusEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<OrderStatusEntity> orderStatusEntityList;
}