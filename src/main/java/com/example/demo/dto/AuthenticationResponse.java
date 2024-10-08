package com.example.demo.dto;

public class AuthenticationResponse {
    private final UserDTO user;
    private final String token;

    public AuthenticationResponse(UserDTO user, String token) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }
}
