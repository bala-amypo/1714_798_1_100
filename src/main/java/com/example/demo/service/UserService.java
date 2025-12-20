package com.example.demo.service;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.User;

public interface UserService {
    User register(User user) throws ValidationException; // Add this method
    User findByEmail(String email);
    User getUser(Long id);
}