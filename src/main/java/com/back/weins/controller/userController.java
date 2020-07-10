package com.back.weins.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Tools.UserAndAvatar;
import com.back.weins.entity.Avatar;
import com.back.weins.entity.user;
import com.back.weins.repository.AvatarRepository;
import com.back.weins.repository.userRepository;
import com.back.weins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.sun.webkit.perf.WCFontPerfLogger.log;


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

//    @GetMapping(value = "/user/reg", produces = "application/json;charset=UTF-8")
//    public Integer insertUser(user use) {
//        if(userRep.check(use.getPhone())==0) return 0;
//        BigDecimal time = BigDecimal.valueOf(new Date().getTime() / 1000.0);
//        use.setType(7);
//        use.setReg_time(time);
//        user save = userRep.save(use);
//        return 1;
//    }

    @GetMapping(value = "/user/reg", produces = "application/json;charset=UTF-8")
    public Integer insertUser(String name, Integer sex, String phone, String password, String birthday, String base64) {

        if(userRep.check(phone)==0) return 0;
        user use = new user();
        use.setName(name);
        use.setBirthday(birthday);
        use.setSex(sex);
        use.setPassword(password);
        use.setPhone(phone);
        BigDecimal time = BigDecimal.valueOf(new Date().getTime() / 1000.0);
        use.setType(7);
        use.setReg_time(time);
        userRep.save(use);
        if(base64.equals("default")) return 1;
        Avatar avatar = new Avatar();
        avatar.setBookId(use.getUid());
        avatar.setImgBase64(base64);
        avatarRepository.save(avatar);
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
    @RequestMapping(value="/user/login", produces="application/json;charset=UTF-8")
    public JSONObject Login(String phone, String password){
        JSONObject object = new JSONObject();
        if(userRep.check(phone)==1) {
            object.put("res",false);
            return object;
        }
        user u = userRep.findByPhone(phone);
        if(!password.equals(u.getPassword())) {
            object.put("res",false);
            return object;
        }
        Avatar avatar = avatarRepository.findByUid(u.getUid());
        //System.out.print(avatar);
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
        object.put("base64", avatar.getImgBase64());
        return object;
    }


}
