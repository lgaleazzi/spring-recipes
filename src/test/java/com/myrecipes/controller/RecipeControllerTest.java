package com.myrecipes.controller;

import com.myrecipes.exception.RecipeNotFoundException;
import com.myrecipes.model.Category;
import com.myrecipes.model.Recipe;
import com.myrecipes.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest
{
    private MockMvc mockMvc;

    @InjectMocks
    private RecipeController controller;
    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void home_ShouldRenderIndexView() throws Exception
    {
        List<Recipe> recipes = Arrays.asList(
                new Recipe.RecipeBuilder("Cookies", Category.DESSERT)
                        .withDescription("Delicious chocolate cookies")
                        .withPrepTime(15)
                        .withCookTime(30)
                        .build(),

                new Recipe.RecipeBuilder("Chocolate Cake", Category.DESSERT)
                        .withDescription("Fantastic chocolate cake")
                        .withPrepTime(20)
                        .withCookTime(60)
                        .build()
        );
        when(recipeService.findAll()).thenReturn(recipes);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/index"))
                .andExpect(model().attribute("recipes", recipes));
        verify(recipeService).findAll();
    }

    @Test
    public void add_ShouldReturnAddForm() throws Exception
    {
        mockMvc.perform(get("/recipes/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/form"))
                .andExpect(model().attribute("recipe", new Recipe()));
    }

    @Test
    public void add_ShouldRedirectToNewRecipe() throws Exception
    {
        mockMvc.perform(
                fileUpload("/recipes")
                        .file(new MockMultipartFile("file", "test".getBytes()))
                        .param("name", "quiche")
                        .param("description", "quiche lorraine")
                        .param("category", "LUNCH")
                        .param("prepTime", "15")
                        .param("cookTime", "40")
                        .param("ingredients[0].id", "1")
                        .param("ingredients[0].item", "Milk")
                        .param("ingredients[0].condition", "Fresh")
                        .param("ingredients[0].quantity", "1L")
                        .param("steps[0].id", "1")
                        .param("steps[0].stepName", "step 1")
        )
                .andExpect(redirectedUrl("/"))
                .andExpect(status().is3xxRedirection());
        verify(recipeService).save(any(Recipe.class), any(MultipartFile.class));
    }

    @Test
    public void detail_ShouldReturnRecipe() throws Exception
    {
        Recipe recipe = new Recipe.RecipeBuilder("Cookies", Category.DESSERT)
                .withDescription("Delicious chocolate cookies")
                .withPrepTime(15)
                .withCookTime(30)
                .build();
        recipe.setId(1L);
        when(recipeService.findById(1L)).thenReturn(recipe);

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/detail"))
                .andExpect(model().attribute("recipe", recipe));
        verify(recipeService).findById(1L);
    }

    @Test
    public void detail_ShouldReturnNotFoundIfIDNotExist() throws Exception
    {
        when(recipeService.findById(1L)).thenThrow(new RecipeNotFoundException("No recipe with id 1 was found"));

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void delete_ShouldRedirectToIndex() throws Exception
    {
        mockMvc.perform(get("/recipes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(recipeService).delete(1L);
    }

    @Test
    public void delete_ShouldReturnNotFoundIfIDNotExist() throws Exception
    {
        doThrow(new RecipeNotFoundException("No recipe with id 1 was found")).when(recipeService).delete(1L);

        mockMvc.perform(get("/recipes/1/delete"))
                .andExpect(status().is4xxClientError());
    }


}