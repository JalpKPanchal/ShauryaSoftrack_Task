package com.jalp.controller;

import com.jalp.dto.ResponseDTO;
import com.jalp.dto.UserDTO;
import com.jalp.dto.ValidationErrorDTO;
import com.jalp.entity.UserEntity;
import com.jalp.repository.UserRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (userRepository.existsByMobileNumber(userDTO.getMobileNumber())) {
            return new ResponseEntity<>(
                new ResponseDTO("Mobile number already registered", null),
                HttpStatus.BAD_REQUEST
            );
        }

        UserEntity user = new UserEntity();
        user.setName(userDTO.getName());
        user.setMobileNumber(userDTO.getMobileNumber());

        UserEntity savedUser = userRepository.save(user);

        return new ResponseEntity<>(
            new ResponseDTO("User registered successfully", savedUser.getId()),
            HttpStatus.CREATED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ValidationErrorDTO(error.getField(), error.getDefaultMessage()));
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
