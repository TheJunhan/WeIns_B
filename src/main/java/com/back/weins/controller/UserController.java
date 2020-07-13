package com.back.weins.controller;

import com.back.weins.entity.User;
import com.back.weins.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/getOne")
    public User getOne(@RequestParam("id") Integer id) {
        return userService.getByID(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/reg")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam("ph") String phone, @RequestParam("pwd") String password) {
        return userService.login(phone, password);
    }

    @PostMapping("/update")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }
}
