package com.example.racekatte.presentation;

import com.example.racekatte.application.CatService;
import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Gender;
import com.example.racekatte.entity.Race;
import com.example.racekatte.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/catpost")
    public String showCatPostForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        Cat cat = new Cat();
        cat.setRace(new Race()); // Sikre den ikke er null, blev ved med at give fejl, det her ser ud til at have løst det :)
        model.addAttribute("cat", cat); // Tilføjer vores kat til model objektet
        List<Race> races = catService.getAllRaces();
        model.addAttribute("races", races); // Tilføjer vores racer til model objektet
        model.addAttribute("genders", Gender.values());
        return "catpost";
    }

    @PostMapping("/cat/upload") // Modtager dataen fra formualren
    public String uploadCat(@RequestParam("name") String name,
                            @RequestParam("age") int age,
                            @RequestParam("gender") String gender,
                            @RequestParam("description") String description,
                            @RequestParam("race") int raceId,
                            @RequestParam("image") MultipartFile file,
                            HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        try {
            String originalFilename = file.getOriginalFilename(); // Finder originalt filnavn
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); // Finder filtype
            String uniqueFilename = UUID.randomUUID().toString() + extension; // Generere unikt filnavn med RandomUUID

            String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath(); // Her finder vi upload stien
            File destination = new File(uploadDir + "/" + uniqueFilename); //Opretter et objekt med stien
            file.transferTo(destination); //gemmer filen i destination?

            Cat newCat = new Cat(); // Opretter vores katte objekt
            newCat.setName(name);
            newCat.setAge(age);
            newCat.setGender(gender);
            newCat.setDescription(description);
            newCat.setUserId(currentUser.getId());

            if (raceId != 0) { // Hvis race bliver valgt eller ignorer
                Race race = new Race();
                race.setId(raceId);
                newCat.setRace(race);
            }
            newCat.setImg(uniqueFilename); // Sætter billedet med det unikke navn

            catService.createCat(newCat); // Opretter katten
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Der opstod en fejl under upload af billedet.");
            return "catpost";
        }
        return "redirect:/home";
    }
}
