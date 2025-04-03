package com.example.racekatte.presentation;

import com.example.racekatte.application.UserService;
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


    @GetMapping("/") // Gør så vi kan skrive localhost8080 og få localhost808+/login i stedet for at skulle bruge en index
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
    public String register(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) { // Håndtere registering af ny profil, binder data til vores user og genter confirm parameter
        boolean success = loginService.register(user, confirmPassword); // Kalder vores login for at reg bruger
        if (success) { // Hvis success går vi til login side
            return "redirect:/login";
        } else { // Hvis ikke
            model.addAttribute("registrationError", "Forkert bekræft kodeord eller email allerede i brug"); // Fejlbesked
            model.addAttribute("showRegistrationModal", true); // Bliver på vores modal, sikre at den ikke lukker
            model.addAttribute("user", new User()); // Gør så vi får en blank side, vi kan fjerne new hvis vi vil have den beholder mail, ved ikke hvad der er bedst
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

