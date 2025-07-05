package com.societymanagement.authentication_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequest {
    @NotBlank(message = "Bearer token is required")
    public String bearerToken;
}
