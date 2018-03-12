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
        //if no new file was uploaded, associate the object with the image already uploaded
        if (file.isEmpty())
        {
            byte[] image = recipeRepository.findOne(recipe.getId()).getImage();
            recipe.setImage(image);
            recipeRepository.save(recipe);
        } else
        //otherwise upload the new file
        {
            try
            {
                recipe.setImage(file.getBytes());
                recipeRepository.save(recipe);
            } catch (IOException e)
            {
                throw new FileUploadException("The file could not be uploaded");
            }
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
