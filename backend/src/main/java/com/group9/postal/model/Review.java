package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private User customer;

    private int rating;

    @Nullable
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Review(Order order, User customer, int rating, String comment) {
        this.order    = order;
        this.customer = customer;
        this.rating   = rating;
        this.comment  = comment;
    }
}
