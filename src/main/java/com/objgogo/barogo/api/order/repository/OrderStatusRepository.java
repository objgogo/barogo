package com.objgogo.barogo.api.order.repository;

import com.objgogo.barogo.api.order.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity,Long> {
}
