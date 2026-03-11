package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private User customer;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotEmpty
    private int pickupAddress;

    @NotEmpty
    private int dropoffAddress;

    @Nullable
    private BigDecimal totalCost;

    @NotEmpty
    private String orderStatus;

    public Order(User customer, int pickupAddress, int dropoffAddress,
                 BigDecimal totalCost, String orderStatus) {
        this.customer = customer;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
        this.totalCost = totalCost;
        this.orderStatus = orderStatus;
    }


}
