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
        if (email == null) { // Tjekker om der er blevet angivet en mail
            throw new IllegalArgumentException("Mail skal angives");
        }
        if (password == null) { // Tjekker om der er engivet et kodeord
            throw new IllegalArgumentException("Password skal angives");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Bruger ikke fundet");
        }

        if (password != null && !password.trim().isEmpty()) { // Hvis password ikke er tomt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Hasher det nye kodeord
            user.setPassword(hashedPassword); // Opdatere kodeord til den nye hashed værdi
        }

        user.setEmail(email); // Sætter brugerens email fra hentet parameter og indsætter værdier nedeunder hvis angivet
        if (firstName != null) user.setFirstName(firstName);
        if (lastName != null) user.setLastName(lastName);
        if (postalCode != null) user.setPostalCode(postalCode);
        if (phoneNumber != null) user.setPhoneNumber(phoneNumber);


        userRepository.updateUser(user); // Opdatere user i db med de nye værider i user objektet
    }
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers(String search) { // Henter alle brugere, samt mulighedw for søgning
        List<User> allUsers = userRepository.findAllUsers(); // Henter alle brugere fra db
        List<Cat> allCats = catRepository.findAllCatsAndUsers(); // Henter alle katte og brugere fra db

        if (search == null || search.trim().isEmpty()) { //Tjekker om der er et søgeterm
            return allUsers; // Returnere alle users hvis ingen søgning
        }

        String searchLower = search.trim().toLowerCase(); // Konverterer søgningen til små bogstaver og trimmer
        List<User> filteredUsers = new ArrayList<>(); // Opretter en tom liste til filt brugere

        for (User user : allUsers) { // Går igennem alle brugere
            boolean matchesUser = (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(searchLower)) ||
                    (user.getLastName() != null && user.getLastName().toLowerCase().contains(searchLower)); // Tjekker om fornavn og efter navn ermatcher søgning

            boolean matchesCat = false; // Sætter den false som standard
            for (Cat cat : allCats) { // Går igennem alle katte
                if (cat.getUserId() == user.getId() && cat.getName() != null && cat.getName().toLowerCase().contains(searchLower)) {
                    matchesCat = true; // Hvis en kat matcher sætter vi true
                    break;
                }
            }

            if (matchesUser || matchesCat) { // Hvis bruger eller kat stemmer overens, tilføjer vi brugeren
                filteredUsers.add(user);
            }
        }

        return filteredUsers; // Returnere liste af brugere
    }

}
