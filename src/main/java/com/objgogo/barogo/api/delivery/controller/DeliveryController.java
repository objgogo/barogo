package com.objgogo.barogo.api.delivery.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@Api(tags ={"[사용자] - Delivery API"}, protocols = "http", produces = "application/json", consumes = "application/json" )
@RequestMapping("/api/delivery")
public class DeliveryController {
}
