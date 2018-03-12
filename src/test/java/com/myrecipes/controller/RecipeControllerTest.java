package com.myrecipes.controller;

import com.myrecipes.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest
{
    private MockMvc mockMvc;
    private RecipeController controller;
    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void home_ShouldRenderIndexView() throws Exception
    {
        mockMvc.perform(get("/"))
                .andExpect(view().name("recipe/index"));
    }


}