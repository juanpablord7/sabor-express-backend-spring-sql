package com.miempresa.serviceorder.controller;

import com.miempresa.serviceorder.dto.request.OrderRequest;
import com.miempresa.serviceorder.service.OrderService;
import feign.Body;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/me")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getMyOrders(){

        return ResponseEntity.ok("Exito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){

        return ResponseEntity.ok("Exito");
    }

    @PostMapping()
    public ResponseEntity<?> postOrder(@Valid @RequestBody OrderRequest order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

}
