package com.peachub.app.controller;

import com.peachub.app.entity.User;
import com.peachub.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private final UserService userService;
    public RegistrationController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(User user){
        userService.createUser(user);
        return "redirect:/";
    }
}
