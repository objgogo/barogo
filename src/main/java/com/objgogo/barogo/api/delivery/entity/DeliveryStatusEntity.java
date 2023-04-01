package com.objgogo.barogo.api.delivery.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_status")
@Getter
@Setter
public class DeliveryStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = DeliveryEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private DeliveryEntity delivery;

    @Column(name = "status")
    private String status;

    @Column(name = "create_dt")
    private LocalDateTime crateDt;


}
