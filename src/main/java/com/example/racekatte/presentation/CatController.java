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
import org.springframework.web.bind.annotation.*;
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
        User currentUser = (User) session.getAttribute("currentUser"); // Henter aktive user fra session
        if (currentUser == null) {
            return "redirect:/login";
        }
        Cat cat = new Cat(); // Opretter tomt katte opbjekt
        cat.setRace(new Race()); // initialisere raceobjekt - fiksede vores null problem
        model.addAttribute("cat", cat); // Tilføjer vores kat til model
        List<Race> races = catService.getAllRaces(); // Henter alle racer fra db
        model.addAttribute("races", races); // Tilføjer vores racer til model
        model.addAttribute("genders", Gender.values()); // Tilføjer mulige køn
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
        User currentUser = (User) session.getAttribute("currentUser"); // Henter user session igen igen
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

            Cat newCat = new Cat(); // Opretter et nyt katte objekt til oprettelse
            newCat.setName(name); // Sætter navn
            newCat.setAge(age);
            newCat.setGender(gender);
            newCat.setDescription(description);
            newCat.setUserId(currentUser.getId());

            if (raceId != 0) { // Hvis race bliver valgt eller ignorer
                Race race = new Race(); // Opretter et nyt raceobjekt
                race.setId(raceId); // Sætter raceid fra input
                newCat.setRace(race); // tilknutter racen til katten
            }
            newCat.setImg(uniqueFilename); // Sætter billedet med det unikke navn

            catService.createCat(newCat); // Gemmer den nye kat i db gennem catservice
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Der opstod en fejl under upload af billedet.");
            return "catpost";
        }
        return "redirect:/home";
    }
    @GetMapping("catEdit/{id}")
    public String showEditForm(@PathVariable("id") int catId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Brug eksisterende metode i service og filtrér selv
        Cat cat = catService.findCatsByUserId(currentUser.getId()) // Henter alle katte for den akutelle bruger
                .stream()
                .filter(c -> c.getId() == catId) // Starter en stream ^ for a filtrere listen, Filtrere kun katten med det angivet id fra kald, finder den første med id.
                .findFirst()
                .orElse(null); // returnere null hvis der ikke findes en kat med det id

        if (cat == null) {
            return "redirect:/user"; // redirect til user hvis katten ikke findes
        }

        List<Race> races = catService.getAllRaces(); //Henter alle racer fra db til vores drop down
        model.addAttribute("cat", cat); // Tilføjer vores katte objekt til modellen for af have formularen udfyldt med nuværende data
        model.addAttribute("races", races); // Tilføjer racer til modellen så de kan bruges i dropdown
        return "catEdit";
    }
    @PostMapping("/cat/update")
    public String updateCat(HttpSession session,
                            @RequestParam("id") int id,
                            @RequestParam("name") String name,
                            @RequestParam("age") int age,
                            @RequestParam("gender") String gender,
                            @RequestParam("description") String description,
                            @RequestParam("race") int raceId,
                            @RequestParam("oldImg") String oldImg,
                            @RequestParam("image") MultipartFile file) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        String finalImgName = oldImg; // Brug det gamle billede, hvis ikke der er et nyt billede

        // Hvis der er uploadet et nyt billede, gem det
        try {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = UUID.randomUUID().toString() + extension;

                String uploadDir = new File("src/main/resources/static/uploads").getAbsolutePath();
                File destination = new File(uploadDir + "/" + uniqueFilename);
                file.transferTo(destination);

                finalImgName = uniqueFilename; // Erstat med det nye billede
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/user"; // Fejl ved billedeupload
        }

        // Brug CatService til at opdatere katten
        boolean updated = catService.updateCat(id, currentUser.getId(), name, age, gender, description, raceId, finalImgName);

        if (updated) {
            return "redirect:/user"; // Hvis opdatering lykkedes, omdiriger til brugerens side
        } else {
            return "redirect:/user"; // Hvis opdatering fejlede, omdiriger tilbage
        }
    }
}

