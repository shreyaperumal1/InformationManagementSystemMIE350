package com.group9.postal.repository;

import com.group9.postal.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderStatus(String status);
    List<Order> findByCustomerUserId(Long customerId);
}