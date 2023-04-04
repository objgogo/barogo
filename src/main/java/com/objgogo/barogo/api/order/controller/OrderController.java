package com.objgogo.barogo.api.order.controller;

import com.objgogo.barogo.api.order.service.OrderService;
import com.objgogo.barogo.api.order.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    @ApiOperation(value = "사용자 Order 목록 조회")
    public ResponseEntity<List<OrderInfo>> orderList(@ModelAttribute SearchOrderRequest req){
        return ResponseEntity.ok(orderService.getOrderList(req));
    }

    @PostMapping("/change/orderTo")
    @ApiOperation(value = "사용자 Order 도착지 주소 변경 API")
    public ResponseEntity<ChangeOrderToResponse> changeOrderTo(ChangeOrderToRequest req) throws Exception {
        return ResponseEntity.ok(orderService.changeOrderTo(req));

    }

}
