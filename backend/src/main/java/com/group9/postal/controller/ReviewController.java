package com.group9.postal.controller;

import com.group9.postal.controller.exceptions.ReviewNotFoundException;
import com.group9.postal.dto.CreateReviewRequest;
import com.group9.postal.model.Order;
import com.group9.postal.model.Review;
import com.group9.postal.model.User;
import com.group9.postal.repository.OrderRepository;
import com.group9.postal.repository.ReviewRepository;
import com.group9.postal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class ReviewController {
    @Autowired
    private final ReviewRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/reviews")
    List<Review> retrieveAllReviews() {
        return repository.findAll();
    }

    @GetMapping("/reviews/{id}")
    Review retrieveReview(@PathVariable("id") Long reviewId) {
        return repository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    @GetMapping("/reviews/customer/{customerId}")
    List<Review> retrieveReviewsByCustomer(@PathVariable("customerId") Long customerId) {
        return repository.findByCustomerUserId(customerId);
    }

    @GetMapping("/reviews/order/{orderId}")
    List<Review> retrieveReviewsByOrder(@PathVariable("orderId") Long orderId) {
        return repository.findByOrderOrderId(orderId);
    }

    @PostMapping("/reviews")
    Review createReview(@RequestBody CreateReviewRequest req) {

        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User customer = userRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setOrder(order);
        review.setCustomer(customer);
        review.setRating(req.getRating());
        review.setComment(req.getComment());

        return repository.save(review);
    }

    @PutMapping("/reviews/{id}")
    Review updateReview(@RequestBody Review newReview, @PathVariable("id") Long reviewId) {
        return repository.findById(reviewId)
                .map(review -> {
                    review.setRating(newReview.getRating());
                    review.setComment(newReview.getComment());
                    return repository.save(review);
                })
                .orElseGet(() -> {
                    newReview.setReviewId(reviewId);
                    return repository.save(newReview);
                });
    }

    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable("id") Long reviewId) {
        repository.deleteById(reviewId);
    }
}
