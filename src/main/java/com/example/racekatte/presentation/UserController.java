package com.example.racekatte.presentation;

import com.example.racekatte.application.CatService;
import com.example.racekatte.application.UserService;
import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.User;
import com.example.racekatte.infrastructure.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CatService catService;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository, CatService catService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.catService = catService;
        ;
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



    @PostMapping("/user")
    public String updateUser(HttpSession session, @RequestParam String email, @RequestParam String password,
                             @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                             @RequestParam(required = false) String postalCode, @RequestParam(required = false) String phoneNumber) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        userService.updateUser(currentUser.getId(), email, password, firstName, lastName, postalCode, phoneNumber); // Opdatere bruger data gennem userService

        session.setAttribute("currentUser", userRepository.findById(currentUser.getId()));

        return "redirect:/user";
    }

    @PostMapping("/delete-profile")
    public String deleteUser(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            userService.deleteUser(currentUser.getId()); // Sletter brugeren fra DB, gå igennem userService
            session.invalidate(); // invalidater sessionen og logger dem ud.
        }
        return "redirect:/login";
    }

    @PostMapping("/delete-cat")
    public String deleteCat(@RequestParam("catId") int catId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        catService.deleteCat(catId); // Sletter kat fra db igennem catService
        return "redirect:/user";
    }

    @GetMapping("/members")
    public String showMembers(HttpSession session, Model model, @RequestParam(required = false) String search) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<User> users = userService.getAllUsers(search); // Henter en filteret liste af brugere baseret på søgening
        for (User user : users) { // Går igennem alle brugere i listen
            List<Cat> cats = catService.findCatsByUserId(user.getId()); // Henter katte for alle brugere i db
            user.setCats(cats); // Tilknytter katten vores user objekt
        }

        model.addAttribute("users", users); // Tilføjer users til modellen
        model.addAttribute("search", search); // Tilf'ker søgning til modellen
        return "members";
    }
}
