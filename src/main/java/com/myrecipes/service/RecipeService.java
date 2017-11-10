package com.myrecipes.service;

import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService
{
    List<Recipe> findAll();
    List<Category> allCategories();
    Recipe findById(Long id);
    void save(Recipe recipe, MultipartFile file);
}
