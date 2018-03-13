package com.myrecipes.core.config;

import com.myrecipes.model.User;
import com.myrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<User>
{
    @Autowired
    private UserService userService;

    @Override
    public User getCurrentAuditor()
    {
        User currentAuditor = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated())
        {
            org.springframework.security.core.userdetails.User springUser =
                    (org.springframework.security.core.userdetails.User)authentication.getPrincipal();

            currentAuditor = userService.findByUsername(springUser.getUsername());
        }

        return currentAuditor;
    }
}
