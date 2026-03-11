package com.group9.postal.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank
    private String pickupAddress;

    @NotBlank
    private String dropoffAddress;

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
                 String pickupAddress, String dropoffAddress,
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