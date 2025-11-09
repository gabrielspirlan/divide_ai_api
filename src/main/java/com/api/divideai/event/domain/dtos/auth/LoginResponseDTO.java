package com.api.divideai.event.domain.dtos.auth;

public class LoginResponseDTO {

    private String token;
    private String userId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

