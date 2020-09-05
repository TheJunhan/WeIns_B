package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.JwtTokenUtil;
import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.entity.User;
import com.back.weins.servicesImpl.UserServiceImpl;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
//    logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getOne")
    public User getOne(@RequestParam("id") Integer id) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info(" get user by id: "+id);
        return userService.getByID(id);
    }

    @GetMapping("/getByName")
    public User getByName(@RequestParam("name") String name) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info(" get user by name: "+name);
        return userService.getByName(name);
    }

    @GetMapping("/getByFuzzyName")
    public List<User> getByFuzzyName(@RequestParam("name") String name) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info(" get user by fuzzyname: "+name);
        return userService.getByFuzzyName(name);
    }

    @GetMapping("/getPlainOne")
    public User getPlainOne(@RequestParam("id") Integer id) {
        User user = userService.getByID(id);
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        if (user == null) {
            LOG.info(" the finding user: "+id+" does not exist");
        }
        else  {
            LOG.info(" found user by id: "+id);
        }
        user.setPassword(null);
        return user;
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info("get all users");
        return userService.getAll();
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterUtil registerUtil) {
//        return userService.register(registerUtil);
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        String result=userService.register(registerUtil);
        if (result == "phone error") {
            LOG.info("user: "+registerUtil.getPhone()+" register phone error");
        }
        else if (result == "name error") {
            LOG.info("user: "+registerUtil.getName()+" register name error");
        }
        else{
            LOG.info("user: "+registerUtil.getPhone()+" register successfully pwd: "+registerUtil.getPassword());
        }

        return result;
    }

    @PostMapping("/login")
    public User login(@RequestParam("ph") String phone, @RequestParam("pwd") String password){
//        logger LOG = LoggerFactory.getLogger(UserController.class);
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        User user1 = userService.login(phone, password);
        if (user1.getId() == (-1)) {
            LOG.info("user: "+phone+" does not exist, pwd: "+password);
        }
        else if (user1.getId() == (-2)) {
            LOG.info("user: "+phone+" failed to login, wrong pwd: "+password);
        }
        else{
            LOG.info("user: "+phone+" login successfully pwd: "+password);
        }
        return user1;
    }

    @PostMapping("/tokenLogin")
    public User tokenLogin(@RequestParam("token") String token) throws Exception {
        String res = token.replace(" ", "");
        Claims claims = jwtTokenUtil.parseJWT(res);

        String subject = claims.getSubject();

        JSONObject jsonObject = JSON.parseObject(subject);

        User user = JSON.toJavaObject(jsonObject, User.class);

        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info(" token login : "+token);
        return login(user.getPhone(), user.getPassword());
    }

    @PostMapping("/update")
    public String update(@RequestBody RegisterUtil registerUtil) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        LOG.info("user: "+registerUtil.getPhone()+" update message");
        return userService.update(registerUtil, null);
    }

    @PostMapping("/follow")
    public String follow(@RequestParam("sub") Integer sub, @RequestParam("obj") Integer obj,
                    @RequestParam("flag") Integer flag) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        if (Objects.equals(sub, obj))
        {
            LOG.info("user: "+sub+" followed himself");
            return "self";
        }


        // 1 means follow and -1 means un follow
        if (flag == 1 || flag == -1) {
            userService.follow_relation(sub, obj, flag);
            if(flag == 1)
            {
                LOG.info("user: "+sub+" followed "+obj);
            }
            else
            {
                LOG.info("user: "+sub+" unfollowed "+obj);
            }
            return "success";
        }

        else
            return "flag";
    }

    @PostMapping("/auth")
    public String auth(@RequestParam("sub") Integer sub, @RequestParam("obj") Integer obj,
                       @RequestParam("tar") Integer target) {
        Logger LOG = LoggerFactory.getLogger(UserController.class);
        if (target < -8 || target >= 8)
        {
            LOG.info("user: "+sub+" request wrong permission ");
            return "target error";
        }


        if (Objects.equals(sub, obj))
        {
            LOG.info("user: "+sub+" request  permission from himself");
            return "self";
        }
//            return "self";
        LOG.info("user: "+sub+" request "+target+" permission from user: "+obj );
        return userService.auth(sub, obj, target);
    }

    @RequestMapping("/parsejwt")
    public void parseJwt(@RequestParam("token") String token) throws Exception {
        if(token == null) return ;

        String res = token.replace(" ", "");
        Claims claims = jwtTokenUtil.parseJWT(res);
        String subject = claims.getSubject();
        JSONObject jsonObject = JSON.parseObject(subject);
        User user = JSON.toJavaObject(jsonObject, User.class);
        System.out.print(user);
    }
}
