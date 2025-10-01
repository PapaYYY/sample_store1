package com.griddynamics.mamaievm.samplestoreapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(OrderController.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

    public static final String BASE_URL = "/api/v1/order";
//    private final OrderService orderService;
}
