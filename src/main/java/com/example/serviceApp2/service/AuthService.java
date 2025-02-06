package com.example.serviceApp2.service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.serviceApp2.config.JwtUtil;
import com.example.serviceApp2.entity.User;
import com.example.serviceApp2.repo.UserRepo;

@Service
public class AuthService {
    
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> register(User user) {
    	if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
    
    public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
 
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password!");
        }

        return jwtUtil.generateToken(username);
    }
	
}
