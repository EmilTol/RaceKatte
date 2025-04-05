package com.example.racekatte.entity;

import java.util.List;

public class Cat {
    private int id;
    private int userId;
    private String name;
    private int age;
    private String gender;
    private String description;
    private String img;
    private User user;
    private Race race;
    private List<Race> races;

    public Cat() {
    }

    public Cat(int id, int userId, String name, int age, String gender, String description, String img) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.img = img;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public List<Race> getRaces() {
        return races;
    }
    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
