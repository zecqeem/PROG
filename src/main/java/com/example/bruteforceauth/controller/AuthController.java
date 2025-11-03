package com.example.bruteforceauth.controller;

import com.example.bruteforceauth.dto.LoginRequest;
import com.example.bruteforceauth.dto.LoginResponse;
import com.example.bruteforceauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody(required = false) LoginRequest request,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(value = "password", required = false) String password) {
        // If the request comes with form parameters
        if (username != null && password != null) {
            request = new LoginRequest(username, password);
        }

        return authService.login(request);
    }
}