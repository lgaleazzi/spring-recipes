package com.myrecipes.controller;

import com.myrecipes.model.User;
import com.myrecipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public String signupForm(Model model)
    {
        if (!model.containsAttribute("user"))
        {
            User user = new User();
            model.addAttribute("user", user);
        }

        return "user/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String addUser(User user)
    {
        userService.save(user);
        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request)
    {
        model.addAttribute("user", new User());
        try
        {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);

            request.getSession().removeAttribute("flash");
        } catch (Exception ex)
        {
            // "flash" session attribute must not exist...do nothing and proceed normally
        }
        return "user/login";
    }

    @RequestMapping("/profile")
    public String profilePage(Model model, Authentication authentication)
    {
        if (authentication == null || !authentication.isAuthenticated())
        {
            return "redirect:/login";
        }

        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);

        return "user/profile";
    }

    @RequestMapping(value = "/users")
    public String allUsers(Model model)
    {
        model.addAttribute("users", userService.findAll());

        return "user/all";
    }
}
