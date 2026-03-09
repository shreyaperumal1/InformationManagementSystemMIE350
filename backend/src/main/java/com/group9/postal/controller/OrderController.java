package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.OrderNotFoundException;
import com.group9.postal.model.Order;
import com.group9.postal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    List<Order> retrieveAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/orders/{id}")
    Order retrieveOrder(@PathVariable("id") Long orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @GetMapping("/orders/customer/{customerId}")
    List<Order> retrieveOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return repository.findByCustomerUserId(customerId);
    }

    @GetMapping("/orders/status/{status}")
    List<Order> retrieveOrdersByStatus(@PathVariable("status") String status) {
        return repository.findByOrderStatus(status);
    }

    @PostMapping("/orders")
    Order createOrder(@RequestBody Order newOrder) {
        return repository.save(newOrder);
    }

    @PutMapping("/orders/{id}")
    Order updateOrder(@RequestBody Order newOrder, @PathVariable("id") Long orderId) {
        return repository.findById(orderId)
                .map(order -> {
                    order.setPickupAddress(newOrder.getPickupAddress());
                    order.setDropoffAddress(newOrder.getDropoffAddress());
                    order.setTotalCost(newOrder.getTotalCost());
                    order.setOrderStatus(newOrder.getOrderStatus());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setOrderId(orderId);
                    return repository.save(newOrder);
                });
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable("id") Long orderId) {
        repository.deleteById(orderId);
    }
}
