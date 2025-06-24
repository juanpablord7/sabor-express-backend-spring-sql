package com.miempresa.serviceorder.service;

import com.miempresa.serviceorder.client.IndexClient;
import com.miempresa.serviceorder.dto.OrderRequest;
import com.miempresa.serviceorder.dto.PaginatedResponse;
import com.miempresa.serviceorder.model.OrderModel;
import com.miempresa.serviceorder.repository.OrderCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderCrudRepository orderCrudRepository;
    private final IndexClient indexClient;

    public OrderService(OrderCrudRepository orderCrudRepository, IndexClient indexClient) {
        this.orderCrudRepository = orderCrudRepository;
        this.indexClient = indexClient;
    }

    public PaginatedResponse<OrderModel> findAllOrder(Integer page, Integer limit, Long status){
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderModel> ordersPage;

        if (status == null) {
            ordersPage = orderCrudRepository.findAll(pageable);
        } else {
            ordersPage = orderCrudRepository.findByState(status);
        }

        return new PaginatedResponse<>(
                ordersPage.getContent(),
                ordersPage.getNumber(),
                ordersPage.getTotalPages(),
                ordersPage.getTotalElements(),
                ordersPage.getSize()
        );
    }

    public PaginatedResponse<OrderModel> findAllByStatus(Integer page, Integer limit, Long status){
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderModel> ordersPage;

        Boolean isChef = true;
        Boolean isDelivery = true;

        if(isChef == false && isDelivery == false){
            return new PaginatedResponse<>();
        }

        // Validaciones de transiciones
        if (isChef) {
            if(status == 0L){
                ordersPage = orderCrudRepository.findByState(0L);
            }else if(status == 1L){
                ordersPage = orderCrudRepository.findByState(1L);
            }

        }

        if (isDelivery) {
            if(status == 2L){
                ordersPage = orderCrudRepository.findByState(2L);
            }else if(status == 3L){
                ordersPage = orderCrudRepository.findByState(3L);
            }
        }

        return new PaginatedResponse<>(
                ordersPage.getContent(),
                ordersPage.getNumber(),
                ordersPage.getTotalPages(),
                ordersPage.getTotalElements(),
                ordersPage.getSize()
        );
    }

    public OrderModel createOrder(OrderRequest request){
        OrderModel order = OrderModel.builder()
                                    .order(request.getOrder())
                                    .userId(request.getUserId())
                                    .build();

        order.setState(0L);

        order.setId(indexClient.getIndex());

        System.out.println("Order to be created: " + order.toString());

        return orderCrudRepository.save(order);
    }

    public OrderModel replaceOrder(Long id, OrderRequest request){
        OrderModel actualOrder;

        System.out.println("Id of the Order to be replaced: " + id);

        actualOrder = orderCrudRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        System.out.println("Order to be replaced was found");

        OrderModel order = OrderModel.builder()
                                    .id(actualOrder.getId())
                                    .order(request.getOrder())
                                    .userId(request.getUserId())
                                    .build();

        System.out.println("Order to replace: " + order.toString());

        return orderCrudRepository.save(order);
    }

    public OrderModel updateOrder(Long id, OrderRequest request){
        OrderModel actualOrder;

        System.out.println("Id of the Order to be updated: " + id);

        actualOrder = orderCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        System.out.println("Order to be updated was found");

        if(request.getOrder() != null){
            actualOrder.setOrder(request.getOrder());
        }
        if(request.getUserId() != null){
            actualOrder.setUserId(request.getUserId());
        }

        System.out.println("Order to update: " + actualOrder.toString());

        return orderCrudRepository.save(actualOrder);
    }

    public OrderModel updateOrderState(Long id, Long userId) {
        OrderModel order = orderCrudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Long currentState = order.getState();

        Boolean isChef = true;
        Boolean isDelivery = true;

        // Validaciones de transiciones
        if (isChef && (currentState == 0L || currentState == 1L)) {
            order.setState((currentState + 1L));
        }

        if (isDelivery && currentState == 2L) {
            order.setState((currentState + 1L));
        }

        return orderCrudRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderCrudRepository.deleteById(id);
    }

}
