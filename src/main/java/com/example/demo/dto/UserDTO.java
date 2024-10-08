package com.example.demo.dto;


import com.example.demo.entity.User;

public class UserDTO {
    private final int userId;
    private final String userName;
    private final String email;
    private final String fullName;
    private final Integer phoneNumber;
    private final String photo;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.photo = user.getPhoto();
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }
}