package com.example.racekatte.presentation;

import com.example.racekatte.entity.User;
import com.example.racekatte.application.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedIn = loginService.login(user.getEmail(), user.getPassword());
        if (loggedIn != null) {
            session.setAttribute("currentUser", loggedIn);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Forket kode eller email");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        boolean success = loginService.register(user, confirmPassword);
        if (success) {
            return "redirect:/login";
        } else {
            model.addAttribute("Kunne ikke registere", "Forkert bekræft kodeord eller email allerede i brug");
            model.addAttribute("showRegistrationModal", true);
            model.addAttribute("user", user);
            return "login";
        }
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user";
    }
}

