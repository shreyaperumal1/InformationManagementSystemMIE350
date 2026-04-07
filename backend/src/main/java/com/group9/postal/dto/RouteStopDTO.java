package com.group9.postal.dto;

import com.group9.postal.model.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteStopDTO {
    private Long stopId;
    private int stopSequence;

    private Long addressId;

    private String address; // ✅ formatted string

    private String stopType;

    private LocalDateTime plannedTime;
    private LocalDateTime completedTime;

    private List<StopShipmentDTO> stopShipments;
}
