package com.miempresa.serviceorder.controller;

import com.miempresa.serviceorder.dto.PaginatedResponse;
import com.miempresa.serviceorder.dto.request.order.OrderPatchRequest;
import com.miempresa.serviceorder.dto.request.order.OrderRequest;
import com.miempresa.serviceorder.dto.response.OrderView;
import com.miempresa.serviceorder.model.Order;
import com.miempresa.serviceorder.service.OrderService;
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
    public ResponseEntity<PaginatedResponse<OrderView>> getMyOrders(@RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "10") int limit,
                                                                    @RequestParam(required = false) Long state){

        return ResponseEntity.ok(orderService.findMyOrders(page, limit, state));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getMyOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findMyOrderById(id));
    }

    @PostMapping()
    public ResponseEntity<Order> postOrder(@Valid @RequestBody OrderRequest order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> putOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderRequest order){
        return ResponseEntity.ok(orderService.replaceOrder(id, order));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderPatchRequest order){
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("The Order was deleted successfully");
    }

}
