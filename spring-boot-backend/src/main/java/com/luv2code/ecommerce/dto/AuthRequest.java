package com.luv2code.ecommerce.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
