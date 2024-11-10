package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String photo = "https://firebasestorage.googleapis.com/v0/b/movieticket-77cf5.appspot.com/o/aef0b5d5-c0fa-475c-86f9-fc5106a8f046-avatar-null.jpg?alt=media";
    private int role = 0;
    private LocalDateTime createDate = LocalDateTime.now();
    private String status = "Đang hoạt động";
    private boolean isDelete = false;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-rate")
    private List<Rate> rates;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-favourite")
    private List<Favourite> favourite;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-buyticket")
    private List<BuyTicket> buyticket;
}
