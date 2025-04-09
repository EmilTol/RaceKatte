package com.example.racekatte.application;

import com.example.racekatte.entity.User;
import com.example.racekatte.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateUser(int userId, String email, String password, String firstName, String lastName, String postalCode, String phoneNumber) {
        if (email == null) {
            throw new IllegalArgumentException("Mail skal angives");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password skal angives");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Bruger ikke fundet");
        }

        if (password != null && !password.trim().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        user.setEmail(email);
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (postalCode != null) user.setPostalCode(postalCode);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);


        userRepository.updateUser(user);
    }
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

}
