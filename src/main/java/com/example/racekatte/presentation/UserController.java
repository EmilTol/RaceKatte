package com.example.racekatte.presentation;

import com.example.racekatte.application.UserService;
import com.example.racekatte.entity.User;
import com.example.racekatte.infrastructure.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostMapping("/user")
    public String updateUser(HttpSession session, @RequestParam String email, @RequestParam String password,
                             @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                             @RequestParam(required = false) String postalCode, @RequestParam(required = false) String phoneNumber) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        userService.updateUser(currentUser.getId(),email, password, firstName, lastName, postalCode, phoneNumber);

        session.setAttribute("currentUser", userRepository.findById(currentUser.getId()));

        return "redirect:/user";
    }
    @PostMapping("/delete-profile")
    public String deleteUser(HttpSession session) {
    User currentUser = (User) session.getAttribute("currentUser");

    if (currentUser != null) {
        userService.deleteUser(currentUser.getId());
        session.invalidate();
    }
    return "redirect:/login";
    }

}
