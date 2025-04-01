package com.example.racekatte.application;

import com.example.racekatte.entity.User;
import com.example.racekatte.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean register(User user, String confirmPassword) {
        if (!user.getPassword().equals(confirmPassword)) {
            return false;
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return userRepository.registerUser(user);
    }
}
