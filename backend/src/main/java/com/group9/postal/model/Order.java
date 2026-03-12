package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pickup_address_id", nullable = false)
    private Address pickupAddress;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dropoff_address_id", nullable = false)
    private Address dropoffAddress;

    @NotBlank
    private String contactName;

    @NotBlank
    private String contactEmail;

    @NotBlank
    private String contactPhone;

    private BigDecimal totalCost;

    @NotBlank
    private String orderStatus;

    public Order(String contactName, String contactEmail, String contactPhone,
                 Address pickupAddress, Address dropoffAddress,
                 BigDecimal totalCost, String orderStatus) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.pickupAddress = pickupAddress;
        this.dropoffAddress = dropoffAddress;
        this.totalCost = totalCost;
        this.orderStatus = orderStatus;
    }
}