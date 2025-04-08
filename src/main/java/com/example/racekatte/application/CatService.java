package com.example.racekatte.application;

import com.example.racekatte.entity.Cat;
import com.example.racekatte.entity.Race;
import com.example.racekatte.infrastructure.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteCat(int catId) {
        catRepo.deleteCatById(catId);
    }
}
