package com.myrecipes.core;

import com.myrecipes.model.Ingredient;
import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import com.myrecipes.repository.RecipeRepository;
import com.myrecipes.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements ApplicationRunner
{
    private final RecipeRepository recipeRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository)
    {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {

        List<Ingredient> ingredients = new ArrayList<>(Arrays.asList(
                new Ingredient("Eggs", "Fresh", "3"),
                new Ingredient("Milk", "Fresh", "0.5L")
        ));

        List<Step> steps = new ArrayList<>(Arrays.asList(
                new Step("First do this"),
                new Step("And then do that"),
                new Step("Finally do this")
        ));

        List<Recipe> recipes = Arrays.asList(
                new Recipe.RecipeBuilder("Cookies", Category.DESSERT)
                        .withDescription("Delicious chocolate cookies")
                        .withPrepTime(15)
                        .withCookTime(30)
                        .withIngredients(ingredients)
                        .withSteps(steps)
                        .build(),

                new Recipe.RecipeBuilder("Chocolate Cake", Category.DESSERT)
                        .withDescription("Fantastic chocolate cake")
                        .withPrepTime(20)
                        .withCookTime(60)
                        .build()
        );

        recipes.forEach(recipeRepository::save);
    }
}
