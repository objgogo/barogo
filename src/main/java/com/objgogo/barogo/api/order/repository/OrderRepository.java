package com.objgogo.barogo.api.order.repository;

import com.objgogo.barogo.api.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
}
