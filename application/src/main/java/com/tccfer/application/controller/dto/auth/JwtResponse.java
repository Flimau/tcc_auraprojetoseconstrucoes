package com.tccfer.application.controller.dto.auth;

import lombok.Getter;

@Getter
public class JwtResponse {

    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
