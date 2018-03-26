package com.myrecipes.service;

import com.myrecipes.exception.UserNotFoundException;
import com.myrecipes.model.Recipe;
import com.myrecipes.model.User;
import com.myrecipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id)
    {
        User user = userRepository.findOne(id);
        if (user == null)
        {
            throw new UserNotFoundException(String.format("No user with id %s was found", id));
        }

        return user;
    }

    @Override
    public User findByUsername(String username)
    {
        User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null)
        {
            throw new UserNotFoundException(String.format("No user with username %s was found", username));
        }
        return user;
    }

    @Override
    public void save(User user)
    {
        userRepository.save(user);
    }

    @Override
    public void toggleFavorite(User user, Recipe recipe)
    {
        user.toggleFavorite(recipe);
        userRepository.save(user);
    }

    @Override
    public boolean isFavorite(User user, Recipe recipe)
    {
        if (user.getFavorites() != null)
        {
            return user.getFavorites().contains(recipe);
        }
        return false;
    }

    @Override
    public List<User> findAll()
    {
        return userRepository.findAll();
    }
}
