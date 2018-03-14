package com.myrecipes.service;

import com.myrecipes.model.Recipe;
import com.myrecipes.model.User;

import java.util.List;

public interface UserService
{
    User findById(Long id);

    User findByUsername(String username);

    void save(User user);

    void toggleFavorite(User user, Recipe recipe);

    boolean isFavorite(User user, Recipe recipe);

    List<User> findAll();
}
