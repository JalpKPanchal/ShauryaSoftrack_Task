package com.jalp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jalp.dto.UserDTO;
import com.jalp.entity.UserEntity;
import com.jalp.repository.UserRepository;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.existsByMobileNumber(userDTO.getMobileNumber())) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Mobile number already registered");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setMobileNumber(userDTO.getMobileNumber());
        
        UserEntity savedUser = userRepository.save(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedUser.getId());
        response.put("message", "User registered successfully");
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}