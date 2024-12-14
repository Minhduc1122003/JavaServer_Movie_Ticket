package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.UpdatePasswordDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FirebaseStorageService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll/except")
    public List<User> getAllUserExcept(@RequestParam String username){
    	return userService.getAllUsersExcept(username);
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/getPhotoById/{id}")
    public ResponseEntity<String> getPhotoById(@PathVariable int id){
    	String img = userService.getPhotoById(id);
    	return img != null ? ResponseEntity.ok(img) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/getTicketById/{id}")
    public List<TicketDTO> getAllTicketById(@PathVariable int id){
    	return userService.getAllTicketByUserId(id);
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
    	if(userRepository.findByEmail(user.getEmail()) != null) {
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã tồn tại!");
    	}
    	if(userRepository.findByUserName(user.getUserName()) != null) {
    		return ResponseEntity.status(HttpStatus.CONFLICT).body("Tài khoản đã tồn tại!");
    	}
    	userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Đăng ký thành công !");
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    
    @PutMapping("/update/password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Integer id, @RequestBody String password) {
    	password = password.replace("\"", "").trim();
    	System.out.println("Password check: " + password);
    	userService.updatePassword(id, password);
        return ResponseEntity.ok("Update success");
    }
    
    @PutMapping("/update/passwordByUser")
    public ResponseEntity<String> updatePasswordByUser(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
    	int userId = updatePasswordDTO.getUserId();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại!");
        }
        
    	try {
    		if(passwordEncoder.matches(updatePasswordDTO.getPasswordOld(), user.getPassword())) {
    			user.setPassword(updatePasswordDTO.getPasswordNew());
        		userService.updateUser(userId, user);
        		return ResponseEntity.ok("Update success");
        	} else {
        		return ResponseEntity.status(HttpStatus.CONFLICT).body("Mật khẩu cũ không đúng!");
        	}
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error !!");
		}
    }
    
    @PutMapping("/update-avatar")
    public ResponseEntity<String> updateAvatar(@RequestParam("userId") int userId, @RequestParam("file") MultipartFile file) {
        try {
            // Tải lên file và lấy URL của ảnh
            String imgUrl = firebaseStorageService.uploadFile(file);
            
            // Cập nhật ảnh đại diện cho người dùng
            boolean isUpdated = userService.updateAvatar(userId, imgUrl);
            
            // Kiểm tra kết quả và trả về phản hồi tương ứng
            if (isUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Avatar updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi trong quá trình cập nhật");
        }
    }
    
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}