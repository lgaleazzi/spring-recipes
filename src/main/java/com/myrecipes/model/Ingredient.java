package com.myrecipes.model;

import com.myrecipes.model.Recipe;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Ingredient
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String item;

    @NotBlank
    private String condition;

    @NotBlank
    private String quantity;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(String item, String condition, String quantity, Recipe recipe)
    {
        this.item = item;
        this.condition = condition;
        this.quantity = quantity;
        this.recipe = recipe;
    }

    public Ingredient()
    {
        this(null, null, null, null);
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getItem()
    {
        return item;
    }

    public void setItem(String item)
    {
        this.item = item;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
}
