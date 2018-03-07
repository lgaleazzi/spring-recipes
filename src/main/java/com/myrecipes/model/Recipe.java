package com.myrecipes.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    @Lob
    private byte[] image;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    private int prepTime;

    private int cookTime;

    //Using Set so Hibernate can load both collections
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "id")
    private List<Ingredient> ingredients;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "id")
    private List<Step> steps;

    public Recipe(RecipeBuilder builder)
    {
        this.name = builder.name;
        this.image = builder.image;
        this.description = builder.description;
        this.category = builder.category;
        this.prepTime = builder.prepTime;
        this.cookTime = builder.cookTime;
        if (builder.ingredients != null)
        {
            this.ingredients = builder.ingredients;
        }
        if (builder.steps != null)
        {
            this.steps = builder.steps;
        }
    }

    public Recipe()
    {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public Recipe(String name, byte[] image, String description, Category category, int prepTime, int cookTime)
    {
        this();
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
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

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
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

    //List needed by Thymeleaf to access elements in form
    //public List<Ingredient> getIngredientsList() {return new ArrayList<>(ingredients);}

    //public void setIngredientsList(List<Ingredient> ingredients) {this.ingredients = new HashSet<>(ingredients);}

    public List<Step> getSteps()
    {
        return steps;
    }

    public void setSteps(List<Step> steps)
    {
        this.steps = steps;
    }

/*    //List needed by Thymeleaf to access elements in form
    public List<Step> getStepsList()
    {
        return new ArrayList<>(steps);
    }

    public void setStepsList(List<Step> steps)
    {
        this.steps = new HashSet<>(steps);
    }*/

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

    public static class RecipeBuilder
    {
        private String name;
        private byte[] image;
        private String description;
        private Category category;
        private int prepTime;
        private int cookTime;
        private List<Ingredient> ingredients;
        private List<Step> steps;

        public RecipeBuilder(String name, Category category)
        {
            this.name = name;
            this.category = category;
        }

        public RecipeBuilder withImage(byte[] image)
        {
            this.image = image;
            return this;
        }

        public RecipeBuilder withDescription(String description)
        {
            this.description = description;
            return this;
        }

        public RecipeBuilder withPrepTime(int prepTime)
        {
            this.prepTime = prepTime;
            return this;
        }

        public RecipeBuilder withCookTime(int cookTime)
        {
            this.cookTime = cookTime;
            return this;
        }

        public RecipeBuilder withIngredients(List<Ingredient> ingredients)
        {
            this.ingredients = ingredients;
            return this;
        }

        public RecipeBuilder withSteps(List<Step> steps)
        {
            this.steps = steps;
            return this;
        }

        public Recipe build()
        {
            return new Recipe(this);
        }

    }
}
