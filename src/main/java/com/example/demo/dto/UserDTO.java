package com.example.demo.dto;


import java.util.Date;

import com.example.demo.entity.User;

public class UserDTO {
    private final int userId;
    private final String userName;
    private final String email;
    private final String fullName;
    private final Integer phoneNumber;
    private final String photo;
    private final int role;
    private final Date createDate;
    private final String status;
    private final Boolean isDelete;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.photo = user.getPhoto();
        this.role = user.getRole(); // Giả sử có phương thức getRole() trong User
        this.createDate = user.getCreateDate(); // Giả sử có phương thức getCreateDate() trong User
        this.status = user.getStatus(); // Giả sử có phương thức getStatus() trong User
        this.isDelete = user.getIsDelete(); // Giả sử có phương thức getIsDelete() trong User
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
    // Getters
    public int getRole() {
        return role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

}