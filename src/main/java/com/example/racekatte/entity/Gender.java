package com.example.racekatte.entity;

public enum Gender { // Ikke i brug endnu, men tror den kan gøre det nemmere for os at håndtere data til vores db da vi har en enum der.
    HAN, HUN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
