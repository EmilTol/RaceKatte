package com.example.racekatte.application;

import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.infrastructure.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {

    private final CatRepository catRepo;

    @Autowired
    public CatService(CatRepository catRepo) {
        this.catRepo = catRepo;
    }

    public List<Cat> getAllCatsAndUsers() {
        return catRepo.findAllCatsAndUsers();
    }

    public boolean createCat(Cat cat) {
        return catRepo.registerCat(cat);
    }

    public List<Race> getAllRaces() {
        return catRepo.getAllRaces();
    }
    public List<Cat> findCatsByUserId(int userId) {
        return catRepo.findCatsByUserId(userId);
    }

    public List<Cat> filteredCatsBySearch(String search, Integer raceId, Integer minAge, Integer maxAge) {
        List<Cat> allCats = catRepo.findAllCatsAndUsers();
        List<Cat> filteredCats = new ArrayList<>();

        for (Cat cat : allCats) {
            boolean matchesSearch = search == null || search.trim().isEmpty() ||
                    (cat.getName() != null && cat.getName().toLowerCase().contains(search.toLowerCase())) ||
                    (cat.getDescription() != null && cat.getDescription().toLowerCase().contains(search.toLowerCase()));

            boolean matchesRace = raceId == null ||
                    (cat.getRace() != null && cat.getRace().getId() == raceId);

            boolean matchesAge = (minAge == null || cat.getAge() >= minAge) &&
                    (maxAge == null || cat.getAge() <= maxAge);

            if (matchesSearch && matchesRace && matchesAge) {
                filteredCats.add(cat);
            }
        }
        return filteredCats;
    }

    public void deleteCat(int catId) {
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
