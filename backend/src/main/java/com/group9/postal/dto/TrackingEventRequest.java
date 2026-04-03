package com.group9.postal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingEventRequest {
    private Long shipmentId;
    private String status;
    private String locationText;
    private String note;
}