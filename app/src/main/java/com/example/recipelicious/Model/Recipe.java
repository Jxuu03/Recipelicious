package com.example.recipelicious.Model;

public class Recipe {
    String name;
    String ingredients;
    String instructions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {return instructions;}

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
