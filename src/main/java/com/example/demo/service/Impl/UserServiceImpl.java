package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}