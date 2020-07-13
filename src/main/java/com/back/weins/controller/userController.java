package com.back.weins.controller;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Avatar;
import com.back.weins.entity.User;
import com.back.weins.repository.AvatarRepository;
import com.back.weins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AvatarRepository avatarRepository;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @GetMapping(value="/user/{uid}", produces = "application/json;charset=UTF-8")
    public User getUser(@PathVariable("uid") Integer uid){
        return userRepository.findById(uid).orElse(null);
    }

    @GetMapping(value = "/user/reg", produces = "application/json;charset=UTF-8")
    public Integer insertUser(String name, Integer sex, String phone, String password, String birthday, String base64) {
        if(userRepository.check(phone)==0) return 0;
        User use = new User();
        use.setName(name);
        use.setBirthday(birthday);
        use.setSex(sex);
        use.setPassword(password);
        use.setPhone(phone);
        use.setType(7);
        Date date = new Date();
        use.setReg_time(format.format(date));
        userRepository.save(use);
        if(base64.equals("default")) return 1;
        Avatar avatar = new Avatar();
        avatar.setBase64(base64);
        avatarRepository.save(avatar);
        return 1;
    }

    @GetMapping(value="/alluser", produces = "application/json;charset=UTF-8")
    public List<User> AllUser(){
        return userRepository.findAll();
    }
    @GetMapping(value="/uploadimage")
    public boolean UpImage(Avatar avatar){
        avatarRepository.save(avatar);
        return true;
    }

    @RequestMapping(value="/user/login", produces="application/json;charset=UTF-8")
    public JSONObject Login(String phone, String password){
        JSONObject object = new JSONObject();
        if(userRepository.check(phone)==1) {
            object.put("res",false);
            return object;
        }
        User u = userRepository.findByPhone(phone);
        if(!password.equals(u.getPassword())) {
            object.put("res",false);
            return object;
        }
        Avatar avatar = avatarRepository.findById(u.getUid()).get();

        object.put("res", true);
        object.put("uid", u.getUid());
        object.put("name", u.getName());
        //object.put("phone", u.getPhone());
        object.put("birthday", u.getBirthday());
        object.put("reg_time", u.getReg_time());
        object.put("type", u.getType());
        if(avatar == null) {
            object.put("base64", "default");
            return object;
        }
        object.put("base64", avatar.getBase64());
        return object;
    }
}
