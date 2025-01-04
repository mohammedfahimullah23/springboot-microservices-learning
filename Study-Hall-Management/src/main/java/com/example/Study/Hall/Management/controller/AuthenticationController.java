package com.example.Study.Hall.Management.controller;

import com.example.Study.Hall.Management.dto.LoginDto;
import com.example.Study.Hall.Management.dto.UserDto;
import com.example.Study.Hall.Management.model.Admin;
import com.example.Study.Hall.Management.security.JwtUtil;
import com.example.Study.Hall.Management.service.AuthenticationService;
import com.example.Study.Hall.Management.wrappers.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        Admin user = authenticationService.registerUser(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Admin user = authenticationService.findByUsername(loginDto.getUsername());
        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials"); // fahim refactor this later
    }
}

