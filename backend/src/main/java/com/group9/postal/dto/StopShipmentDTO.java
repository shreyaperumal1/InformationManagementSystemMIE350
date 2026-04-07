package com.group9.postal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StopShipmentDTO {
    private Long shipmentId;
    private String trackingNumber;
    private String action;
    private String type;
}
