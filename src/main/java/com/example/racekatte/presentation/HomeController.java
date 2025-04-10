package com.example.racekatte.presentation;

import com.example.racekatte.application.CatService;
import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final CatService catService;

    @Autowired
    public HomeController(CatService catService) {
        this.catService = catService;
    }

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
}
