package com.group9.postal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest {
    private Long orderId;
    private Long customerId;
    private int rating;
    private String comment;
}