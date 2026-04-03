package com.group9.postal.dto;

import com.group9.postal.model.Address;
import com.group9.postal.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class OrderResponse {

    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private Address pickupAddress;
    private Address dropoffAddress;
    private BigDecimal totalCost;
    private String orderStatus;
    private LocalDateTime createdAt;

}