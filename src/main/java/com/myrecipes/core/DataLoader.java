package com.myrecipes.core;

import com.myrecipes.model.*;
import com.myrecipes.repository.RecipeRepository;
import com.myrecipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner
{
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository, UserRepository userRepository)
    {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        User livia = new User("Livia");
        livia.setPassword("password");
        userRepository.save(livia);

        User admin = new User("Admin");
        admin.setPassword("password");
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);

        List<Ingredient> ingredients1 = new ArrayList<>(Arrays.asList(
                new Ingredient("Eggs", "Fresh", "3"),
                new Ingredient("Milk", "Fresh", "0.5L")
        ));

        List<Ingredient> ingredients2 = new ArrayList<>(Arrays.asList(
                new Ingredient("Eggs", "Fresh", "2"),
                new Ingredient("Chocolate", "Dark", "200g")
        ));

        List<Step> steps1 = new ArrayList<>(Arrays.asList(
                new Step("First do this"),
                new Step("And then do that"),
                new Step("Finally do this")
        ));

        List<Step> steps2 = new ArrayList<>(Arrays.asList(
                new Step("Mix"),
                new Step("Bake")
        ));

        List<Recipe> recipes = Arrays.asList(
                new Recipe.RecipeBuilder("Cookies", Category.DESSERT)
                        .withDescription("Delicious chocolate cookies")
                        .withPrepTime(15)
                        .withCookTime(30)
                        .withIngredients(ingredients1)
                        .withSteps(steps1)
                        .build(),

                new Recipe.RecipeBuilder("Chocolate Cake", Category.DESSERT)
                        .withDescription("Fantastic chocolate cake")
                        .withPrepTime(20)
                        .withCookTime(60)
                        .withIngredients(ingredients2)
                        .withSteps(steps2)
                        .build()
        );


        livia.toggleFavorite(recipes.get(0));

        recipes.forEach(recipeRepository::save);
        userRepository.save(livia);


    }
}
