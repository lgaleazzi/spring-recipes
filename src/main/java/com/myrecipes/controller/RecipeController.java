package com.myrecipes.controller;

import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import com.myrecipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController
{
    @Autowired
    private RecipeService recipeService;

    @ModelAttribute("categories")
    public List<Category> allCategories()
    {
        return recipeService.allCategories();
    }

    @RequestMapping("/")
    public String allRecipes(Model model)
    {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);
        return "recipe/index";
    }

    @RequestMapping("/recipes/{id}")
    public String recipeDetails(@PathVariable Long id, Model model)
    {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/detail";
    }

    @RequestMapping("/recipes/add")
    public String addForm(Model model)
    {
        if (!model.containsAttribute("recipe"))
        {
            model.addAttribute("recipe", new Recipe());
        }

        return "recipe/form";
    }

    @RequestMapping(value = "/recipes", method = RequestMethod.POST)
    public String addRecipe(@Valid Recipe recipe)
    {
        recipeService.save(recipe);
        return "redirect:/";
    }
}
