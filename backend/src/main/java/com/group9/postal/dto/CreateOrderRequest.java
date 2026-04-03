package com.group9.postal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateOrderRequest {
    @NotNull
    private Long customerId;

    @NotBlank
    private String contactName;

    @NotBlank
    @Email
    private String contactEmail;

    @NotBlank
    private String contactPhone;

    @NotNull
    private Long pickupAddressId;

    @NotNull
    private Long dropoffAddressId;

    private BigDecimal totalCost;

}