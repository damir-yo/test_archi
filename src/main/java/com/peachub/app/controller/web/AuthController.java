package com.peachub.app.controller.web;

import com.peachub.app.entity.User;
import com.peachub.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String showRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }
    @PostMapping("/register")
    public String registerUser(User user){
        userService.createUser(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

}