package com.example.Study.Hall.Management.service;

import com.example.Study.Hall.Management.model.Admin;
import com.example.Study.Hall.Management.repository.AdminRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AdminRespository adminRespository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Admin registerUser(String username, String password) {
        Admin user = new Admin();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return adminRespository.save(user);
    }

    public Admin findByUsername(String username) {
        return adminRespository.findByUsername(username);
    }
}
