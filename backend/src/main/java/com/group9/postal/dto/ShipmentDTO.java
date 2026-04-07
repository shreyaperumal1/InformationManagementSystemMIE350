package com.group9.postal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentDTO {
    private Long shipmentId;
    private String trackingNumber;
    private String type;
    private float weight;
    private float volume;
    private boolean fragileFlag;
    private String currentStatus;
    private Long currentWarehouseId;
}
