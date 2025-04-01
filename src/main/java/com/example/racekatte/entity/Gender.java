package com.example.racekatte.entity;

public enum Gender {
    HAN, HUN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
