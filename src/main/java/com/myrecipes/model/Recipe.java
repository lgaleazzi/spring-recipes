package com.myrecipes.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String image;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    private int prepTime;

    private int cookTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Step> steps;

    public Recipe(String name, String image, String description, Category category, int prepTime, int cookTime)
    {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public Recipe()
    {
        this(null, null, null, null, 0, 0);
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public int getPrepTime()
    {
        return prepTime;
    }

    public void setPrepTime(int prepTime)
    {
        this.prepTime = prepTime;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public void setCookTime(int cookTime)
    {
        this.cookTime = cookTime;
    }

    public List<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps()
    {
        return steps;
    }

    public void setSteps(List<Step> steps)
    {
        this.steps = steps;
    }
}
