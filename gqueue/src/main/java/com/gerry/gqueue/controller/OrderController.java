package com.gerry.gqueue.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.gerry.gqueue.dto.OrderRequest;
import com.gerry.gqueue.entity.Order;
import com.gerry.gqueue.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request){
        Order o = orderService.createOrder(request);

        return new ResponseEntity<>(o, HttpStatus.CREATED);
    }
}
