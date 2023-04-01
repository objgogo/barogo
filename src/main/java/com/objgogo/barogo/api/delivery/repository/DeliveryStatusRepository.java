package com.objgogo.barogo.api.delivery.repository;

import com.objgogo.barogo.api.delivery.entity.DeliveryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusEntity, Long> {
}
