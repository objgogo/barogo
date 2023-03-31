package com.objgogo.barogo.api.order.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Api(tags ={"[사용자] - Order API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
@RequestMapping("/api/order")
public class OrderController {
}
