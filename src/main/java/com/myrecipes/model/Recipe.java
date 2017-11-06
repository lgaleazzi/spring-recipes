package com.myrecipes.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
//NamedEntityGraph allows fetching steps and ingredients in repository
@NamedEntityGraph(name = "Recipe.detail", attributeNodes = {
        @NamedAttributeNode("steps"),
        @NamedAttributeNode("ingredients")
})
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

    //Using Set so Hibernate can load both collections
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Step> steps;

    public Recipe(String name, String image, String description, Category category, int prepTime, int cookTime)
    {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        ingredients = new HashSet<>();
        steps = new HashSet<>();
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

    public Set<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public Set<Step> getSteps()
    {
        return steps;
    }

    public void setSteps(Set<Step> steps)
    {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Recipe recipe = (Recipe)o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null)
            return false;
        return name != null ? name.equals(recipe.name) : recipe.name == null;

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
