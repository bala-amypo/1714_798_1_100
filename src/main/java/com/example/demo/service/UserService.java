package com.example.demo.service;

import com.example.demo.model.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    User login(String email, String password); // Add this
    User findByEmail(String email);
    User getById(Long id);
    User getUserById(Long id); // Add this
    List<User> getAllUsers();
    User updateUser(User user); // Add this
    void deleteUser(Long id);
}