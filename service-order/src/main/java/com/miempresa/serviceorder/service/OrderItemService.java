package com.miempresa.serviceorder.service;

import com.miempresa.serviceorder.dto.PaginatedResponse;
import com.miempresa.serviceorder.dto.orderitem.OrderItemRequest;
import com.miempresa.serviceorder.model.OrderItem;
import com.miempresa.serviceorder.repository.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
//Service to crud OrderItem, but to create the orders this is not user"
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public PaginatedResponse<OrderItem> findAll(Integer page, Integer limit, Long productId, Long orderId) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderItem> orderItems;

        if (productId != null && orderId != null) {
            orderItems = orderItemRepository.findByProductIdAndOrder_Id(productId, orderId, pageable);
        } else if (productId != null) {
            orderItems = orderItemRepository.findByProductId(productId, pageable);
        } else if (orderId != null) {
            orderItems = orderItemRepository.findByOrder_Id(orderId, pageable);
        } else {
            orderItems = orderItemRepository.findAll(pageable);
        }

        return new PaginatedResponse<>(
                orderItems.getContent(),
                orderItems.getNumber(),
                orderItems.getTotalPages(),
                orderItems.getTotalElements(),
                orderItems.getSize()
        );
    }

    public OrderItem create(OrderItemRequest request) {
        OrderItem orderItem = OrderItem.builder()
                .order(request.getOrder())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .productName(request.getProductName())
                .build();

        return orderItemRepository.save(orderItem);
    }

    public OrderItem replace(Long id, OrderItem request) {
        OrderItem actualOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        OrderItem orderItem = OrderItem.builder()
                .id(actualOrderItem.getId())
                .order(request.getOrder())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .productName(request.getProductName())
                .build();

        return orderItemRepository.save(orderItem);
    }

    public OrderItem update(Long id, OrderItem request) {
        OrderItem actualOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        if(request.getOrder() != null){
            actualOrderItem.setOrder(request.getOrder());
        }
        if(request.getProductId() != null){
            actualOrderItem.setProductId(request.getProductId());
        }
        if(request.getQuantity() != null){
            actualOrderItem.setQuantity(request.getQuantity());
        }
        if(request.getPrice() != null){
            actualOrderItem.setPrice(request.getPrice());
        }
        if(request.getProductName() != null){
            actualOrderItem.setProductName(request.getProductName());
        }

        return orderItemRepository.save(actualOrderItem);
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
