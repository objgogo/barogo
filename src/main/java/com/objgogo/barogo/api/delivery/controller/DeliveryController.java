package com.objgogo.barogo.api.delivery.controller;

import com.objgogo.barogo.api.delivery.service.DeliveryService;
import com.objgogo.barogo.api.delivery.vo.*;
import com.objgogo.barogo.common.annotaion.PossibleAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@Api(tags ={"[사용자] - Delivery API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
@RequestMapping("/api/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PossibleAccess("DELIVERY")
    @ApiOperation(value = "라이더 주문 수락 API")
    @PostMapping("/take")
    public ResponseEntity<TakeOrderResponse> takeOrder(@Valid @RequestBody TakeOrderRequest req) throws Exception {
        return ResponseEntity.ok(deliveryService.takeOrder(req));
    }

    @PossibleAccess("DELIVERY")
    @ApiOperation(value = "라이더 배달 검색 API")
    @GetMapping("/search")
    public ResponseEntity<List<SearchDeliveryResponse>> searchDelivery(@ModelAttribute SearchDeliveryRequest req){
        return ResponseEntity.ok(deliveryService.searchDelivery(req));
    }

    @PossibleAccess("DELIVERY")
    @ApiOperation(value = "라이더 배달 상태값 변경 API")
    @PostMapping("/take/status")
    public ResponseEntity<ChangeDeliveryStatusResponse> changeDeliveryStatus(@Valid @RequestBody ChangeDeliveryStatusRequest req) throws Exception {
        return ResponseEntity.ok(deliveryService.changeDeliveryStatus(req));
    }

}
