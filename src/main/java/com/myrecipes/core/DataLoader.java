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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<Ingredient> ingredients = new HashSet<>(Arrays.asList(
                new Ingredient("Eggs", "Fresh", "3"),
                new Ingredient("Milk", "Fresh", "0.5L")
        ));

        Set<Step> steps = new HashSet<>(Arrays.asList(
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

        ingredients.forEach(ingredient -> ingredient.setRecipe(recipes.get(0)));
        steps.forEach(step -> step.setRecipe(recipes.get(0)));

        recipes.forEach(recipeRepository::save);
    }
}
