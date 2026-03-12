package com.group9.postal.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private String name;
    private String email;
    private String role;
    private String message;
}
