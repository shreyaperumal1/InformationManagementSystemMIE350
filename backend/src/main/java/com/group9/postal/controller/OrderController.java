package com.group9.postal.controller;

import com.group9.postal.dto.CreateOrderRequest;
import com.group9.postal.dto.OrderResponse;
import com.group9.postal.dto.UpdateOrderStatusRequest;
import com.group9.postal.model.Shipment;
import com.group9.postal.repository.ShipmentRepository;
import com.group9.postal.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ShipmentRepository shipmentRepository;

    public OrderController(OrderService orderService, ShipmentRepository shipmentRepository) {
        this.orderService = orderService;
        this.shipmentRepository = shipmentRepository;
    }

    @GetMapping("/getAll")
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

    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> retrieveOrdersByCustomer(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
    @GetMapping("/{id}/shipments")
    public List<Shipment> getShipmentsByOrder(@PathVariable("id") Long orderId) {
        return shipmentRepository.findByOrderOrderId(orderId);
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
