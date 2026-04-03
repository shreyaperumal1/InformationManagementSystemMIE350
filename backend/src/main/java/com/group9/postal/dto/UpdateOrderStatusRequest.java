package com.group9.postal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOrderStatusRequest {

    @NotBlank
    private String status;

}
