package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dao.UserRepository;
import com.luv2code.ecommerce.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

//    public static class UserDTO {
//        private String firstName;
//        private String lastName;
//
//        public UserDTO(String firstName, String lastName) {
//            this.firstName = firstName;
//            this.lastName = lastName;
//        }
//
//        public String getFirstName() {
//            return firstName;
//        }
//
//        public String getLastName() {
//            return lastName;
//        }
//    }
}
