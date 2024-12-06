package com.example.demo.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.persistence.Tuple;

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
    
    public String getPhotoById(int userId) {
    	return userRepository.findPhotoById(userId);
    }

    public User createUser(User user) {
    	String encodedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    
    public boolean updateAvatar(int userId, String imgUrl) {
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            
            if (imgUrl != null) {
                existingUser.setPhoto(imgUrl);
            }
            
            userRepository.save(existingUser);
            return true;
        } else {
            return false; // Trả về false nếu không tìm thấy người dùng
        }
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
    
    public User updatePassword(int userId, String password) {
    	User existingUser = userRepository.findById(userId).orElse(null);

        if (password != null && !password.isEmpty()) {
        	String encodedPassword = passwordEncoder.encode(password);
            existingUser.setPassword(encodedPassword);
        }

        return userRepository.save(existingUser);
    }
    
    public List<TicketDTO> getAllTicketByUserId(int userId){
    	List<Tuple> tuples = userRepository.getTicketByUserId(userId);
    	
    	return tuples.stream().map(tuple -> {
    		TicketDTO dto = new TicketDTO();
    		
    		dto.setBuyTicketInfoId(tuple.get("buyTicketInfoId", Integer.class));
    		dto.setBuyTicketId(tuple.get("buyTicketId", String.class));
    		dto.setPosterUrl(tuple.get("posterUrl", String.class));
    		dto.setTitle(tuple.get("title", String.class));
    		dto.setShowtimeDate(tuple.get("showtimeDate", Date.class));
    		dto.setStartTime(tuple.get("startTime", Time.class));
    		dto.setChairCodes(tuple.get("chairCodes", String.class));
    		dto.setCinemaName(tuple.get("cinemaName", String.class));
    		dto.setTotalPrice(tuple.get("totalPrice", Double.class));
    		dto.setStatus(tuple.get("status", String.class));
    		return dto;
    	}).collect(Collectors.toList());
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
