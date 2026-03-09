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
    @JoinColumn(name = "customer_id")
    private User customer;

    //Are we using current datetime for this or preassigned?
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotEmpty
    private String pickupAddress;

    @NotEmpty
    private String dropoffAddress;

    @Nullable
    private BigDecimal totalCost;

    @NotEmpty
    private String orderStatus;

    public Order(User customer, String pickupAddress, String dropoffAddress,
                 BigDecimal totalCost, String orderStatus) {
        this.customer = customer;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
        this.totalCost = totalCost;
        this.orderStatus = orderStatus;
    }


}
