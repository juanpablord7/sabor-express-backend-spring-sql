package com.miempresa.serviceorder.service;

import com.miempresa.serviceorder.dto.PaginatedResponse;
import com.miempresa.serviceorder.dto.order.OrderView;
import com.miempresa.serviceorder.model.Order;
import com.miempresa.serviceorder.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManagmentService {
    private final OrderRepository orderRepository;

    public OrderManagmentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //************************** Utilities **************************

    public boolean validateCredentials(String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to perform this action");
        }

        List<String> permissionsClaim = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return permissionsClaim.contains(permission);
    }

    //************************** Operations **************************

    public PaginatedResponse<OrderView> findAllOrder(Integer page, Integer limit, Long state){
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderView> ordersPage;

        if (state == null) {
            ordersPage = orderRepository.findAllProjectedBy(pageable);
        } else {
            ordersPage = orderRepository.findByState(state, pageable);
        }

        return new PaginatedResponse<>(
                ordersPage.getContent(),
                ordersPage.getNumber(),
                ordersPage.getTotalPages(),
                ordersPage.getTotalElements(),
                ordersPage.getSize()
        );
    }

    public Order findById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public Order updateOrderState(Long id, Long newState){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));


        boolean chef = validateCredentials("chef");
        boolean delivery = validateCredentials("delivery");
        if(!chef && !delivery){
            throw new AccessDeniedException("You don't have the permissions to change the state of the order");
        }

        Long actualState = order.getState();

        System.out.println("Actual state: " + actualState);
        System.out.println("New state: " + newState);

        System.out.println("Is chef: " + chef);

        //The CHEF can only change: 1 to 2, 2 to 3
        if(chef) {
            //Change the state to a future or a previous state
            //(But only to the valid states of the chef (1L, 2L, 3L)
            if(newState != null && (1L <= newState && newState <= 3L)){
                order.setState(newState);
            }
            //Change to the nex state, only allowed if the state is 1L or 2L
            else if (1L <= actualState && actualState <= 2L){
                order.setState(order.getState() + 1L);
            }
        }

        System.out.println("Is delivery: " + delivery);

        //The DELIVERY can only change: 3 to 4
        if(delivery) {
            //Change the state to a future or a previous state
            //(But only to the valid states of the delivery (3L, 4L)
            if (newState != null && (3L <= newState && newState <= 4L)) {
                order.setState(newState);
            }
            //Change to the nex state, only allowed if the state is 3L
            else if (3L <= actualState) {
                order.setState(order.getState() + 1L);
            }
        }
        return orderRepository.save(order);
    }
}
