package com.myrecipes.controller;

import com.myrecipes.model.*;
import com.myrecipes.service.RecipeService;
import com.myrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController
{
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

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
        model.addAttribute("selectedCategory", Category.ALL);

        return "recipe/index";
    }

    @RequestMapping("/recipes/{id}")
    public String recipeDetails(@PathVariable Long id, Model model)
    {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/detail";
    }

    @RequestMapping("/recipes/{id}.png")
    @ResponseBody
    public byte[] gifImage(@PathVariable Long id) {
        return recipeService.findById(id).getImage();
    }

    @RequestMapping("/recipes/add")
    @PreAuthorize("isAuthenticated()")
    public String addForm(Model model)
    {
        if (!model.containsAttribute("recipe"))
        {
            Recipe recipe = new Recipe();
            recipe.getSteps().add(new Step());
            recipe.getIngredients().add(new Ingredient());
            model.addAttribute("recipe", recipe);
        }

        model.addAttribute("action", "/recipes");
        return "recipe/form";
    }

    @PostMapping(value = "/recipes")
    @PreAuthorize("isAuthenticated()")
    public String addRecipe(@Valid Recipe recipe, @RequestParam MultipartFile file)
    {
        recipeService.save(recipe, file);
        return "redirect:/";
    }

    @RequestMapping(value = "/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable Long id)
    {
        recipeService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/recipes/{id}/edit")
    public String editForm(@PathVariable Long id, Model model)
    {
        Recipe recipe = recipeService.findById(id);
        if (!model.containsAttribute("recipe"))
        {
            model.addAttribute("recipe", recipe);
        }
        model.addAttribute("action", "/recipes/" + recipe.getId());

        return "recipe/form";
    }

    @RequestMapping(value = "/recipes/{id}", method = RequestMethod.POST)
    public String editRecipe(@Valid Recipe recipe, @RequestParam MultipartFile file)
    {
        recipeService.save(recipe, file);

        return String.format("redirect:/recipes/%s", recipe.getId());
    }

    @RequestMapping(value = "/recipes/{id}/favorite")
    @PreAuthorize("isAuthenticated()")
    public String favoriteRecipe(@PathVariable Long id, Authentication authentication)
    {
        User user = userService.findByUsername(authentication.getName());
        Recipe recipe = recipeService.findById(id);
        userService.toggleFavorite(user, recipe);

        return "redirect:/recipes/" + id;
    }

    @RequestMapping("/recipes/category/{category}")
    public String recipesByCategory(@PathVariable String category, Model model)
    {
        List<Recipe> recipes = recipeService.findByCategory(category);
        model.addAttribute("recipes", recipes);
        model.addAttribute("selectedCategory", Category.valueOf(category));

        return "recipe/index";
    }
}
