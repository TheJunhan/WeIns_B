package com.back.weins.controller;

import com.back.weins.entity.Avatar;
import com.back.weins.entity.user;
import com.back.weins.repository.AvatarRepository;
import com.back.weins.repository.userRepository;
import com.back.weins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class userController {

    @Autowired
    userRepository userRep;
    @Autowired
    AvatarRepository avatarRepository;
    @GetMapping(value="/user/{uid}", produces = "application/json;charset=UTF-8")
    public user getUser(@PathVariable("uid") Integer uid){
        user use = userRep.findById(uid).orElse(null);
        return use;
    }

    @GetMapping(value = "/user/reg", produces = "application/json;charset=UTF-8")
    public Integer insertUser(user use) {
        if(userRep.check(use.getPhone())==0) return 0;
        BigDecimal time = BigDecimal.valueOf(new Date().getTime() / 1000.0);
        use.setType(7);
        use.setReg_time(time);
        user save = userRep.save(use);
        return 1;
    }
    @RequestMapping(value="/alluser", produces = "application/json;charset=UTF-8")
    public List<user> AllUser(user use){
        return userRep.findAll();
    }
    @GetMapping(value="/uploadimage")
    public boolean UpImage(Avatar avatar){
        avatarRepository.save(avatar);
        return true;
    }
//    @Autowired
//    UserService userService;
//    @RequestMapping(value="/user/reg", produces="application/json;charset=UTF-8")
//    public Integer Regist(){
//
//    }
}
