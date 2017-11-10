package com.myrecipes.core;

import com.myrecipes.model.Ingredient;
import com.myrecipes.repository.IngredientRepository;
import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import com.myrecipes.repository.RecipeRepository;
import com.myrecipes.model.Step;
import com.myrecipes.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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
        List<Recipe> recipes = Arrays.asList(
            new Recipe("Cookies", null, "Delicious chocolate cookies",
                    Category.DESSERT, 15, 30),
            new Recipe("Chocolate Cake", null, "Fantastic chocolate cake",
                Category.DESSERT, 20, 60)
        );

        List<Step> steps = Arrays.asList(
                new Step("First do this", recipes.get(0)),
                new Step("And then do that", recipes.get(0)),
                new Step("Finally do this", recipes.get(0))
        );

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("Eggs", "Fresh", "3", recipes.get(0)),
                new Ingredient("Milk", "Fresh", "0.5L", recipes.get(0))
        );

        recipes.get(0).getSteps().addAll(steps);
        recipes.get(0).getIngredients().addAll(ingredients);

        recipes.forEach(recipeRepository::save);
    }
}
