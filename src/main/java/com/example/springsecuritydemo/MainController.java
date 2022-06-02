package com.example.springsecuritydemo;

import com.example.springsecuritydemo.users.domain.Users;
import com.example.springsecuritydemo.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private final UsersService usersService;

    @Autowired
    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping(value = "")
    public @ResponseBody Users registerUser(@RequestBody Users users){
        return usersService.save(users);
    }

    @GetMapping("")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}