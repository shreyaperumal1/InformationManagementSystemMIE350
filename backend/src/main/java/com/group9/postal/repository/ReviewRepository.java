package com.group9.postal.repository;

import com.group9.postal.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCustomerUserId(Long customerId);
    List<Review> findByOrderOrderId(Long orderId);
}
