package com.luv2code.ecommerce.controller;

import com.luv2code.ecommerce.dto.AuthRequest;
import com.luv2code.ecommerce.entity.User;
import com.luv2code.ecommerce.service.UserService;
import com.luv2code.ecommerce.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        // Generate JWT after successful authentication
        return jwtUtil.generateToken(authentication.getName());
    }



//    private final UserService userService;
//
//    @Autowired
//    public AuthController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        if (userService.findByUsername(user.getUsername()).isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(userService.saveUser(user));
//    }
//
////    @GetMapping("/login")
////    public ResponseEntity<User> getCurrentUser() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String currentUsername = authentication.getName();
////        return userService.findByUsername(currentUsername)
////                .map(ResponseEntity::ok)
////                .orElseGet(() -> ResponseEntity.badRequest().build());
////    }
//    @GetMapping("/login")
//    public ResponseEntity<?> getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);
//
//        // Check if the user is authenticated
//        if (authentication == null || !authentication.isAuthenticated()
//                || authentication instanceof AnonymousAuthenticationToken) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
//        }
//
//        String currentUsername = authentication.getName();
//        return userService.findByUsername(currentUsername)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.badRequest().build());
//    }

}