package com.objgogo.barogo.api.order.repository;

import com.objgogo.barogo.api.order.entity.OrderMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity,Long> {
}
