package com.group9.postal.controller;

import com.group9.postal.dto.CreateOrderRequest;
import com.group9.postal.dto.OrderResponse;
import com.group9.postal.dto.UpdateOrderStatusRequest;
import com.group9.postal.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> retrieveAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse retrieveOrder(@PathVariable("id") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponse> retrieveOrdersByStatus(@PathVariable("status") String status) {
        return orderService.getOrdersByStatus(status);
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateOrderStatus(@PathVariable("id") Long orderId,
                                           @Valid @RequestBody UpdateOrderStatusRequest request) {
        return orderService.updateOrderStatus(orderId, request);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
