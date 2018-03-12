package com.myrecipes.service;

import com.myrecipes.exception.FileUploadException;
import com.myrecipes.exception.RecipeNotFoundException;
import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import com.myrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Recipe findById(Long id) throws RecipeNotFoundException
    {
        Recipe recipe = recipeRepository.findOne(id);
        if (recipe == null)
        {
            throw new RecipeNotFoundException(String.format("No recipe with id %s was found", id));
        }
        return recipe;
    }

    @Override
    public void save(Recipe recipe, MultipartFile file)
    {
        System.out.println("attempting to save: " + recipe + file);
        if (!file.isEmpty())
        {
            //if a new file was uploaded, set it as image
            try
            {
                recipe.setImage(file.getBytes());
                recipeRepository.save(recipe);
            } catch (IOException e)
            {
                throw new FileUploadException("The file could not be uploaded");
            }
        } else if (recipe.getId() != null)
        {
            //if no new file was uploaded, but recipe exists, associate object with existing image
            byte[] image = recipeRepository.findOne(recipe.getId()).getImage();
            recipe.setImage(image);
            recipeRepository.save(recipe);
        } else
        {
            recipeRepository.save(recipe);
        }
    }

    @Override
    public void delete(Long id) throws RecipeNotFoundException
    {
        if (recipeRepository.findOne(id) == null)
        {
            throw new RecipeNotFoundException(String.format("No recipe with id %s was found", id));
        }
        recipeRepository.delete(id);
    }
}
