package com.myrecipes.model;

public enum Category
{
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    DESSERT("Dessert");

    private String name;

    Category(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
