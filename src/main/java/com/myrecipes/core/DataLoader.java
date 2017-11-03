package com.myrecipes.core;

import com.myrecipes.recipe.Category;
import com.myrecipes.recipe.Recipe;
import com.myrecipes.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        Recipe recipe = new Recipe("Cookies", "http://mycookies.jpeg", Category.DESSERT, 15, 30);
        recipeRepository.save(recipe);
    }
}
