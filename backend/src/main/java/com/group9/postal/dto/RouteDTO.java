package com.group9.postal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteDTO {
    private Long routeId;
    private Long driverId;
    private Long warehouseId;

    private LocalDateTime plannedStartTime;
    private LocalDateTime plannedEndTime;

    private String routeStatus;

    private List<RouteStopDTO> stops;
}
