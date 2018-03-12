package com.myrecipes.data;


import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;

import java.util.Arrays;
import java.util.List;

public class RecipeData
{

    public static List<Recipe> recipeList()
    {
        return Arrays.asList(recipe1(), recipe2());
    }

    public static Recipe recipe1()
    {
        return new Recipe.RecipeBuilder("Cookies", Category.DESSERT)
                .withDescription("Delicious chocolate cookies")
                .withPrepTime(15)
                .withCookTime(30)
                .build();
    }

    public static Recipe recipe2()
    {
        return new Recipe.RecipeBuilder("Chocolate Cake", Category.DESSERT)
                .withDescription("Fantastic chocolate cake")
                .withPrepTime(20)
                .withCookTime(60)
                .build();
    }
}
