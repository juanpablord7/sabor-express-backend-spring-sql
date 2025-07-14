package com.miempresa.serviceorder.service;

import com.miempresa.serviceorder.client.ProductClient;
import com.miempresa.serviceorder.client.UserClient;
import com.miempresa.serviceorder.dto.PaginatedResponse;
import com.miempresa.serviceorder.dto.client.Product;
import com.miempresa.serviceorder.dto.client.User;
import com.miempresa.serviceorder.dto.order.OrderPatchRequest;
import com.miempresa.serviceorder.dto.order.OrderRequest;
import com.miempresa.serviceorder.dto.order.OrderView;
import com.miempresa.serviceorder.model.Order;
import com.miempresa.serviceorder.model.OrderItem;
import com.miempresa.serviceorder.repository.OrderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final Validator validator;

    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderService(OrderRepository orderRepository, Validator validator, UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.validator = validator;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    //************************ Utilities ************************

    public Order save(Order order){
        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        return orderRepository.save(order);
    }

    public Order createOrderItems(Order order,
                                  List<Long> productsData,
                                  List<Long> quantities){

        if(productsData.size() != quantities.size()){
            throw new IllegalArgumentException("The product and quantity list must have same size");
        }

        //Convert the List into a String delimited by comma
        String productsQuery = convertListProducts(productsData);

        double totalPrice = 0.0;
        List<OrderItem> items = new ArrayList<>();

        //Get the products information from microservice product
        List<Product> products = productClient.findProducts(productsQuery);

        System.out.println("Products: " + products);

        //Create the OrderItems and set the information of each product
        for (int i = 0; i < products.size() ; i++) {
            Product product = products.get(i);
            Long quantity = quantities.get(i);
            double itemTotal = product.getPrice() * quantity;

            OrderItem orderItem = OrderItem.builder()
                    .order(order) //Assign the order
                    .productId(product.getId())
                    .price(product.getPrice())
                    .quantity(quantity)
                    .productName(product.getName())
                    .build();

            items.add(orderItem);
            totalPrice += itemTotal;
        }

        order.setItems(items);
        order.setTotalPrice(totalPrice);

        return order;
    }

    public String convertListProducts(List<Long> products){
        return products.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public Long getUserId(){
        User user = userClient.getUser();

        if(user.getId() == null){
            throw new IllegalArgumentException("The user was not found with your jwt");
        }

        return user.getId();
    }

    //************************ Operations ************************

    public PaginatedResponse<OrderView> findMyOrders(Integer page, Integer limit, Long state){
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderView> ordersPage;

        Long userId = getUserId();

        if(state == null){
            ordersPage = orderRepository.findByUserId(userId, pageable);
        }else{
            ordersPage = orderRepository.findByUserIdAndState(userId, state, pageable);
        }

        return new PaginatedResponse<>(
                ordersPage.getContent(),
                ordersPage.getNumber(),
                ordersPage.getTotalPages(),
                ordersPage.getTotalElements(),
                ordersPage.getSize()
        );
    }

    public Order findMyOrderById(Long id){
        Long userId = getUserId();

        return orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("This order doesn't exist or don't belong to you"));
    }

    public Order createOrder(OrderRequest request){
        int size = request.getProduct().size();

        Long userId = getUserId();

        //Create order to save it in memory and be setted to the orderItems
        Order order = Order.builder()
                .userId(userId)
                .state(1L)
                .build();

        order = createOrderItems(order, request.getProduct(), request.getQuantity());

        order.setCreatedAt(new Date(System.currentTimeMillis()));
        order.setUpdatedAt(new Date(System.currentTimeMillis()));

        //System.out.println("Order to be created: " + order.toString());

        return orderRepository.save(order);
    }

    public Order replaceOrder(Long id, OrderRequest request){
        Order actualOrder = orderRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        //The orderItems can't be modified if the state is 1L (Registered, before pass to cook)
        if(actualOrder.getState() != 1L) {
            throw new IllegalArgumentException("The order is been attended and can't be modified");
        }

        Long userId = getUserId();

        if(!actualOrder.getUserId().equals(userId)){
            throw new IllegalArgumentException("You aren't the owner of this order");
        }

        //Create order to save it in memory and be setted to the orderItems
        Order order = Order.builder()
                .id(actualOrder.getId())
                .userId(userId)
                .build();

        order = createOrderItems(order, request.getProduct(), request.getQuantity());

        order.setUpdatedAt(new Date(System.currentTimeMillis()));

        System.out.println("Order to be edited: " + order.toString());

        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, OrderPatchRequest request){
        Order actualOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Long userId = getUserId();

        if(!actualOrder.getUserId().equals(userId)){
            throw new IllegalArgumentException("You aren't the owner of this order");
        }

        if(request.getProduct() != null ){
            //The orderItems can't be modified if the state is 1L (Registered, before pass to cook)
            if(actualOrder.getState() != 1L) {
                throw new IllegalArgumentException("The order is been attended and can't be modified");
            }

            actualOrder = createOrderItems(actualOrder, request.getProduct(), request.getQuantity());
        }

        actualOrder.setUpdatedAt(new Date(System.currentTimeMillis()));

        System.out.println("Order edited: " + actualOrder.toString());

        return orderRepository.save(actualOrder);
    }

    public void deleteOrder(Long id){
        Order actualOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(actualOrder.getState() != 1L){
            throw new IllegalArgumentException("The order can't be deleted because is being attended");
        }

        Long userId = getUserId();

        if(!actualOrder.getUserId().equals(userId)){
            throw new IllegalArgumentException("You aren't the owner of this order");
        }

        orderRepository.deleteById(id);
    }

}
