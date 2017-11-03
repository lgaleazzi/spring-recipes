package com.myrecipes.recipe;

public enum Category
{
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERT("Dessert");

    private String name;

    private Category(String name)
    {
        this.name = name;
    }
}
