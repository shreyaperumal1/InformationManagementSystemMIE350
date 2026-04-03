package com.group9.postal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopShipmentRequest {
    private Long stopId;
    private Long shipmentId;
    private String action;
}