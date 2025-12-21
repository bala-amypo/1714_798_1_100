// package com.example.demo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     @Autowired
//     UserService userService;

//     @PostMapping
//     public User createUser(@RequestBody User user) {
//         return userService.registerUser(user);
//     }

//     @GetMapping
//     public List<User> getAllUsers() {
//         return userService.getAllUsers();
//     }

//     @GetMapping("/{id}")
//     public Optional<User> getUser(@PathVariable Long id) {
//         return userService.getUserById(id);
//     }

//     @PutMapping("/{id}")
//     public String updateUser(@PathVariable Long id, @RequestBody User newUser) {
//         Optional<User> user = userService.getUserById(id);
//         if (user.isPresent()) {
//             newUser.setId(id);
//             userService.updateUser(newUser);
//             return "User Updated Successfully";
//         }
//         return "User not found";
//     }

//     @DeleteMapping("/{id}")
//     public String deleteUser(@PathVariable Long id) {
//         Optional<User> user = userService.getUserById(id);
//         if (user.isPresent()) {
//             userService.deleteUser(id);
//             return "User Deleted Successfully";
//         }
//         return "User not found";
//     }
// }