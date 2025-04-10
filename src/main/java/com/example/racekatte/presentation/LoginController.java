package com.example.racekatte.presentation;

import com.example.racekatte.application.CatService;
import com.example.racekatte.application.UserService;
import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.entity.User;
import com.example.racekatte.application.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final CatService catService;

    @Autowired
    public LoginController(LoginService loginService, CatService catService) {
        this.loginService = loginService;
        this.catService = catService;
    }


    @GetMapping("/") // Gør så vi kan skrive localhost8080 og få localhost808+/login i stedet for at skulle bruge en index
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("user")) { // Tjekker om der allerede er et user objekt i modellen
            model.addAttribute("user", new User()); // Tilføjer et nyt tomt user objekt
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User loggedIn = loginService.login(user.getEmail(), user.getPassword()); // Kalder login metoden med email og adgangskode
        if (loggedIn != null) { // Hvis logik godkendes
            session.setAttribute("currentUser", loggedIn); // Gemmer den loggede ind bruger i session
            return "redirect:/home"; // Omdirigere til home
        } else { // Hvis login fejler
            model.addAttribute("error", "Forket kode eller email"); // Tilføjer en fejlbesked
            return "login"; // Bliver på login side
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) { // Håndtere registering af ny profil, binder data til vores user og genter confirm parameter
        boolean success = loginService.register(user, confirmPassword); // Kalder registreringsmetoden med brugerdata og bekræftelseskdoe fra formularen
        if (success) { // Hvis success går vi til login side
            return "redirect:/login";
        } else { // Hvis ikke
            model.addAttribute("registrationError", "Forkert bekræft kodeord eller email allerede i brug"); // Fejlbesked
            model.addAttribute("showRegistrationModal", true); // Bliver på vores modal, sikre at den ikke lukker
            model.addAttribute("user", new User()); // Gør så vi får en blank side, vi kan fjerne new hvis vi vil have den beholder mail, ved ikke hvad der er bedst
            return "login";
        }
    }
//    @RequestParam() String search, @RequestParam Integer race, @RequestParam Integer ageMin, @RequestParam Integer ageMax
@GetMapping("/home")
public String home(HttpSession session,
                   @RequestParam(required = false) String search,
                   @RequestParam(required = false) Integer race,
                   @RequestParam(required = false, name = "ageMin") Integer minAge,
                   @RequestParam(required = false, name = "ageMax") Integer maxAge,
                   Model model) {
    User user = (User) session.getAttribute("currentUser"); // Henter den aktuelle bruger fra session

    if (user == null) { // Hvis ingen bruger er logget ind bliver vi smidt tilbage til login
        return "redirect:/login";
    }

    List<Cat> cats = catService.filteredCatsBySearch(search, race, minAge, maxAge); // Henter filter liste baseret på søgning
    List<Race> races = catService.getAllRaces(); // Henter liste medw alle racer fra db

    model.addAttribute("cats", cats);
    model.addAttribute("user", user);
    model.addAttribute("races", races);
    model.addAttribute("search", search);
    model.addAttribute("selectedRace", race);
    model.addAttribute("minAge", minAge);
    model.addAttribute("maxAge", maxAge);

    return "home";
}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // invalidater nuværende session og fjerner data
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser"); // Henter den aktuelle user fra session

        if (user == null) {
            return "redirect:/login";
        }
        List<Cat> cats = catService.findCatsByUserId(user.getId()); // Henter alle katte tilknyttet userid
        model.addAttribute("cats", cats);
        model.addAttribute("user", user);
        return "user";
    }

}

