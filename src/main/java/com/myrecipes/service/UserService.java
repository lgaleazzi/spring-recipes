package com.myrecipes.service;

import com.myrecipes.model.User;

import java.util.List;

public interface UserService
{
    User findById(Long id);

    User findByUsername(String username);

    void save(User user);

    List<User> findAll();
}
