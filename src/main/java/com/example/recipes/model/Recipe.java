package com.example.recipes.model;


import java.util.List;

public record Recipe(String name, String description, String imagePath, List<Ingredient> ingredients) {
}
