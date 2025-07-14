package com.miempresa.serviceorder.controller;

import com.miempresa.serviceorder.dto.state.StateRequest;
import com.miempresa.serviceorder.model.Order;
import com.miempresa.serviceorder.service.OrderManagmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderManagController {
    private final OrderManagmentService orderManagService;

    public OrderManagController(OrderManagmentService orderManagService) {
        this.orderManagService = orderManagService;
    }

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int limit,
                                       @RequestParam(required = false) Long state){

        return ResponseEntity.ok(
                orderManagService.findAllOrder(page, limit, state));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id){

        Order order = orderManagService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/state")
    public ResponseEntity<?> updateOrderState(
            @PathVariable Long id,
            @RequestBody(required = false) StateRequest request){

        Order order = orderManagService.updateOrderState(id, request.getState());
        return ResponseEntity.ok(order);
    }
}
