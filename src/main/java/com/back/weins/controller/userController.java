package com.back.weins.controller;

import com.back.weins.entity.user;
import com.back.weins.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class userController {

    @Autowired
    userRepository userRep;
    @GetMapping(value="/user/{uid}", produces = "application/json;charset=UTF-8")
    public user getUser(@PathVariable("uid") Integer uid){
        user use = userRep.findById(uid).orElse(null);
        return use;
    }

    @GetMapping(value = "/user", produces = "application/json;charset=UTF-8")
    public user insertUser(user use){
        user save = userRep.save(use);
        return save;
    }
    @RequestMapping(value="/alluser", produces = "application/json;charset=UTF-8")
    public List<user> AllUser(user use){
        return userRep.findAll();
    }
}
