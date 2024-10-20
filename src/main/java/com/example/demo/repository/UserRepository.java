package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUserName(String username);
	User findByUserNameAndPassword(String username, String password);
	List<User> findAllByUserNameNot(String username);
}
