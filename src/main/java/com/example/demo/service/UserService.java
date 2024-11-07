package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getAllUsersExcept(String username){
    	return userRepository.findAllByUserNameNot(username);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User createUser(User user) {
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User updateUser(int userId, User user) {
    	User existingUser = userRepository.findById(userId).orElse(null);

        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            existingUser.setUserName(user.getUserName());
        }
        if (user.getFullName() != null && !user.getFullName().isEmpty()) {
            existingUser.setFullName(user.getFullName());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
        	String encodedPassword = passwordEncoder.encode(user.getPassword());
        	user.setPassword(encodedPassword);
            existingUser.setPassword(user.getPassword());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
