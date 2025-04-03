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

    public User login(String email, String password) { // Vores login metode som tager email og password som parameter
        User user = userRepository.findByEmail(email); // Henter user info ved at søge på email i repo
        if (user != null && BCrypt.checkpw(password, user.getPassword())) { // Tjekker om bruger findes og om kodet matcher det den hashed værdi i db, forstår jeg stadig ikke helt
            return user; //
        }
        return null; // Returnere null hvis der er sket en fejl, altså enten forkert email eller kode
    }

    public boolean register(User user, String confirmPassword) { // Vores registrerings metode, med confirm tjek.
        if (!user.getPassword().equals(confirmPassword)) { // Tjekker at kodeord og bekræftkodeord matcher
            return false; // False hvis de ikke matcher
        }
        if (userRepository.findByEmail(user.getEmail()) != null) { // Tjekker om der allerede findes en mail med den mail
            return false; // flase hvis den findes
        }
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()); // Hasher kodeord med bcrypt. + salt hvilket sikre at den samme hashed værdi ikke findes 2 gange tror je
        user.setPassword(hashedPassword); // Sætter brugerens kode til den hashed værdi
        return userRepository.registerUser(user); //Kalder repo og gemmer i repo
    }
}
