package com.example.bruteforceauth.service;

import com.example.bruteforceauth.dto.LoginRequest;
import com.example.bruteforceauth.dto.LoginResponse;
import com.example.bruteforceauth.model.LoginAttempt;
import com.example.bruteforceauth.repository.LoginAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bruteforceauth.model.User;
import com.example.bruteforceauth.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    @Autowired
    private PasswordHASH passwordHASH;

    private static final int MAX_ATTEMPTS = 5;
    private static final int CHILL_MINUTES = 5;
    //TOO MUCH FOR ONE METHOD
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        Optional<User> userOpt = userRepository.findByUsername(username);

        LoginAttempt attempt = loginAttemptRepository.findByUsername(username)
                .orElseGet(() -> {
                    LoginAttempt newAttempt = new LoginAttempt();
                    newAttempt.setUsername(username);
                    newAttempt.setAttempts(0);
                    newAttempt.setBlocked(false);
                    newAttempt.setBlockedAt(LocalDateTime.now());
                    return loginAttemptRepository.save(newAttempt);
                });

        // Check if user is currently blocked
        if (attempt.isBlocked()) {
            if (attempt.getBlockedAt().plusMinutes(CHILL_MINUTES).isAfter(LocalDateTime.now())) {
                return new LoginResponse(false, "Too many failed attempts. Try again later.");
            } else {
                // Unblock user after cooldown
                attempt.setBlocked(false);
                attempt.setAttempts(0);
                loginAttemptRepository.save(attempt);
            }
        }

        if (userOpt.isEmpty()) {
            return new LoginResponse(false, "User not found");
        }

        User user = userOpt.get();

        if (passwordHASH.verify(user.getPassword(), request.getPassword())) {
            // Successful login — reset attempts
            attempt.setAttempts(0);
            attempt.setBlocked(false);
            loginAttemptRepository.save(attempt);
            return new LoginResponse(true, "Login successful");
        } else {
            // Failed login — increment attempt count
            attempt.setAttempts(attempt.getAttempts() + 1);
            attempt.setBlockedAt(LocalDateTime.now());

            if (attempt.getAttempts() >= MAX_ATTEMPTS) {
                attempt.setBlocked(true);
            }

            loginAttemptRepository.save(attempt);

            if (attempt.isBlocked()) {
                return new LoginResponse(false, "Too many failed attempts. User is temporarily blocked.");
            }

            return new LoginResponse(false, "Invalid password");
        }
    }
}