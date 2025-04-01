package com.example.racekatte.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String loginSide( Model model) {

        return "login";
    }

    @GetMapping("/home")
    public String homeTing( Model model) {
        return "home";
    }

    @GetMapping("/user")
    public String userTing( Model model) {
        return "user";
    }
}
