package com.objgogo.barogo.api.delivery.repository;

import com.objgogo.barogo.api.delivery.entity.DeliveryEntity;
import com.objgogo.barogo.api.delivery.entity.DeliveryStatusEntity;
import com.objgogo.barogo.api.order.entity.OrderEntity;
import com.objgogo.barogo.common.DeliveryStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatusEntity, Long> {


    @Query(
            value = "SELECT d FROM DeliveryStatusEntity d WHERE d.delivery.order =:order ORDER BY d.createDt desc"
    )
    List<DeliveryStatusEntity> getOrderDeliveryStatus(@Param("order") OrderEntity order, Pageable pageable);

    Optional<DeliveryStatusEntity> findByDeliveryAndAndStatus(DeliveryEntity delivery, DeliveryStatus status);
}
