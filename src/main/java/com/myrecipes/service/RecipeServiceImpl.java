package com.myrecipes.service;

import com.myrecipes.repository.RecipeRepository;
import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService
{
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll()
    {
        return recipeRepository.findAll();
    }

    @Override
    public List<Category> allCategories()
    {
        return Arrays.asList(Category.values());
    }

    @Override
    public Recipe findById(Long id)
    {
        return recipeRepository.findOne(id);
    }

    @Override
    public void save(Recipe recipe)
    {
        recipeRepository.save(recipe);
    }
}
