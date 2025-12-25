package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // This method would need to be added to UserService
        // return ResponseEntity.ok(userService.getAllUsers());
        return ResponseEntity.ok(List.of()); // Placeholder
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // This method would need to be added to UserService
        // return ResponseEntity.ok(userService.updateUser(id, user));
        return ResponseEntity.ok(user); // Placeholder
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // This method would need to be added to UserService
        // userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}