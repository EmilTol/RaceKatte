package com.example.racekatte.application;

import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.User;
import com.example.racekatte.infrastructure.CatRepository;
import com.example.racekatte.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final CatRepository catRepository;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, CatRepository catRepository) {
        this.userRepository = userRepository;
        this.catRepository = catRepository;
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

    public List<User> getAllUsers(String search) {
        List<User> allUsers = userRepository.findAllUsers();
        List<Cat> allCats = catRepository.findAllCatsAndUsers();

        if (search == null || search.trim().isEmpty()) {
            return allUsers;
        }

        String searchLower = search.trim().toLowerCase();
        List<User> filteredUsers = new ArrayList<>();

        for (User user : allUsers) {
            boolean matchesUser = (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(searchLower)) ||
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(searchLower));

            boolean matchesCat = false;
            for (Cat cat : allCats) {
                if (cat.getUserId() == user.getId() && cat.getName() != null && cat.getName().toLowerCase().contains(searchLower)) {
                    matchesCat = true;
                    break;
                }
            }

            if (matchesUser || matchesCat) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }

}
