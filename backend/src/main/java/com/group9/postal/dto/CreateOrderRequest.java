package com.group9.postal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateOrderRequest {

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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getPickupAddressId() {
        return pickupAddressId;
    }

    public void setPickupAddressId(Long pickupAddressId) {
        this.pickupAddressId = pickupAddressId;
    }

    public Long getDropoffAddressId() {
        return dropoffAddressId;
    }

    public void setDropoffAddressId(Long dropoffAddressId) {
        this.dropoffAddressId = dropoffAddressId;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}