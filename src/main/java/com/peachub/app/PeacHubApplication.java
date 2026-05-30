package com.peachub.app;

import com.peachub.app.entity.User;
import com.peachub.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PeacHubApplication{
    private final UserService userService;

    public PeacHubApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PeacHubApplication.class, args);
    }

//    @Override
//    public void run(String... args) {
//
//        User user = new User();
//
//        user.setUsername("damir");
//        user.setEmail("damir@test.com");
//        user.setPassword("12345");
//
//        userService.createUser(user);
//
//        System.out.println("User saved!");
//    }
}