package com.example.racekatte.application;

import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.infrastructure.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {

    private final CatRepository catRepo;

    @Autowired
    public CatService(CatRepository catRepo) {
        this.catRepo = catRepo;
    }

    public List<Cat> getAllCatsAndUsers() { // laver en liste med alle katte og brugere
        return catRepo.findAllCatsAndUsers();
    }

    public boolean createCat(Cat cat) { //Gemmer det nye cat objekt i db og returnere true hvis det gik godt
        return catRepo.registerCat(cat);
    }

    public List<Race> getAllRaces() { //Laver en liste med alle racer fra vores db
        return catRepo.getAllRaces();
    }
    public List<Cat> findCatsByUserId(int userId) { // Laver en liste af katte fra db som tilhøre det givet userId
        return catRepo.findCatsByUserId(userId);
    }

    public List<Cat> filteredCatsBySearch(String search, Integer raceId, Integer minAge, Integer maxAge) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Opretter et dataformat så vi kan vise oprettelses dato
        List<Cat> allCats = catRepo.findAllCatsAndUsers(); // Henter en liste med alle katte og bruger
        List<Cat> filteredCats = new ArrayList<>(); //Opretter en tom ArrayList til at gemme katte som matcher vores søge kriterier

        for (Cat cat : allCats) {
            boolean matchesSearch = search == null || search.trim().isEmpty() || // Tjekker om søgefeltet er tomt
                    (cat.getName() != null && cat.getName().toLowerCase().contains(search.toLowerCase())) || // Søger i cat.getName, altså søger efter navn.
                    (cat.getDescription() != null && cat.getDescription().toLowerCase().contains(search.toLowerCase())); // Søger i beskrivelse

            boolean matchesRace = raceId == null || // Tjekker om der er blevet valgt en race
                    (cat.getRace() != null && cat.getRace().getId() == raceId); // Tejkker om valgte race matcher raceId

            boolean matchesAge = (minAge == null || cat.getAge() >= minAge) && // Tjekker om kattens alder er over minimum
                    (maxAge == null || cat.getAge() <= maxAge); // Tejkker om kattens alder er under maksimum

            if (matchesSearch && matchesRace && matchesAge) { // Hvis katten opfylder alle søgekriterier
                cat.setPostedDate(sdf.format(cat.getCreatedAt()));
                filteredCats.add(cat); // Tilføjer katten til vores filtrerede liste
            }
        }
        filteredCats.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt())); //Sortere så den nyeste kat viseds først
        return filteredCats; // Returnere filt og sorterede liste
    }

    public void deleteCat(int catId) { // Sletter en kat fra db baseret på dens id
        catRepo.deleteCatById(catId);
    }
    public boolean updateCat(int id, int userId, String name, int age, String gender, String description, int raceId, String img) {
        Cat cat = new Cat();
        cat.setId(id);
        cat.setUserId(userId);
        cat.setName(name);
        cat.setAge(age);
        cat.setGender(gender);
        cat.setDescription(description);
        cat.setImg(img);

        if (raceId != 0) {
            Race race = new Race();
            race.setId(raceId);
            cat.setRace(race);
        }

        return catRepo.updateCat(cat); // Bruger repositoryet til at opdatere katten i databasen
    }

}
