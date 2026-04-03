package com.group9.postal.service;

import com.group9.postal.controller.exceptions.OrderNotFoundException;
import com.group9.postal.dto.CreateOrderRequest;
import com.group9.postal.dto.OrderResponse;
import com.group9.postal.dto.UpdateOrderStatusRequest;
import com.group9.postal.model.Address;
import com.group9.postal.model.Order;
import com.group9.postal.model.User;
import com.group9.postal.repository.AddressRepository;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        validateCreateOrderRequest(request);

        Address pickupAddress = addressRepository.findById(request.getPickupAddressId())
                .orElseThrow(() -> new RuntimeException("Pickup address not found"));

        Address dropoffAddress = addressRepository.findById(request.getDropoffAddressId())
                .orElseThrow(() -> new RuntimeException("Dropoff address not found"));

        Order order = new Order();
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setCustomer(customer);
        order.setContactName(request.getContactName().trim());
        order.setContactEmail(request.getContactEmail().trim());
        order.setContactPhone(request.getContactPhone().trim());
        order.setPickupAddress(pickupAddress);
        order.setDropoffAddress(dropoffAddress);
        order.setTotalCost(request.getTotalCost());
        order.setOrderStatus("Pending");

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        return mapToResponse(order);
    }

    public List<OrderResponse> getOrdersByStatus(String status) {
        return orderRepository.findByOrderStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerUserId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        String currentStatus = order.getOrderStatus();
        String newStatus = request.getStatus().trim();

        boolean validTransition =
                (currentStatus.equalsIgnoreCase("Pending") && newStatus.equalsIgnoreCase("Verified")) ||
                (currentStatus.equalsIgnoreCase("Verified") && newStatus.equalsIgnoreCase("En route")) ||
                (currentStatus.equalsIgnoreCase("En route") && newStatus.equalsIgnoreCase("Drop off"));

        if (!validTransition) {
            throw new RuntimeException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        order.setOrderStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(orderId);
        }
        orderRepository.deleteById(orderId);
    }

    private void validateCreateOrderRequest(CreateOrderRequest request) {
        if (request.getContactName() == null || request.getContactName().trim().isEmpty()) {
            throw new RuntimeException("Contact name is required");
        }

        if (request.getContactEmail() == null || request.getContactEmail().trim().isEmpty()) {
            throw new RuntimeException("Contact email is required");
        }

        if (request.getContactPhone() == null || request.getContactPhone().trim().isEmpty()) {
            throw new RuntimeException("Contact phone is required");
        }

        if (request.getPickupAddressId() == null) {
            throw new RuntimeException("Pickup address ID is required");
        }

        if (request.getDropoffAddressId() == null) {
            throw new RuntimeException("Dropoff address ID is required");
        }
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setContactName(order.getContactName());
        response.setContactEmail(order.getContactEmail());
        response.setContactPhone(order.getContactPhone());
        response.setPickupAddress(order.getPickupAddress());
        response.setDropoffAddress(order.getDropoffAddress());
        response.setTotalCost(order.getTotalCost());
        response.setOrderStatus(order.getOrderStatus());
        response.setCreatedAt(order.getCreatedAt());
        if (order.getCustomer() != null) {
            response.setCustomerId(order.getCustomer().getUserId());
            response.setCustomerEmail(order.getCustomer().getEmail());
        }
        return response;
    }
}