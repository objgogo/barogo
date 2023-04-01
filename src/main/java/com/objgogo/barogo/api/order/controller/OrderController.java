package com.objgogo.barogo.api.order.controller;

import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.RegisterOrderRequest;
import com.objgogo.barogo.api.order.vo.RegisterOrderResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@Api(tags ={"[사용자] - Order API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ApiOperation(value = "Order 등록 API")
    public ResponseEntity<RegisterOrderResponse> registerOrder(@RequestBody RegisterOrderRequest req) throws Exception {
        return ResponseEntity.ok(orderService.registerOrder(req));
    }
}
