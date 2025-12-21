package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    User findByEmail(String email);
    User getById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    
    User login(String email, String password);
}