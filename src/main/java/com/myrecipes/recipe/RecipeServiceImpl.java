package com.myrecipes.recipe;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService
{
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll()
    {
        return Lists.newArrayList(recipeRepository.findAll());
    }
}
