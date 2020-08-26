package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.JwtTokenUtil;
import com.back.weins.entity.User;
import com.back.weins.servicesImpl.UserServiceImpl;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getOne")
    public User getOne(@RequestParam("id") Integer id) {
        return userService.getByID(id);
    }

    @GetMapping("/getByName")
    public User getByName(@RequestParam("name") String name) {
        return userService.getByName(name);
    }

    @GetMapping("/getByFuzzyName")
    public List<User> getByFuzzyName(@RequestParam("name") String name) {
        return userService.getByFuzzyName(name);
    }

    @GetMapping("/getPlainOne")
    public User getPlainOne(@RequestParam("id") Integer id) {
        User user = userService.getByID(id);
        user.setPassword(null);
        return user;
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
    public User login(@RequestParam("ph") String phone, @RequestParam("pwd") String password){
        return userService.login(phone, password);
    }

    @PostMapping("/tokenLogin")
    public User tokenLogin(@RequestParam("token") String token) throws Exception {
        String res = token.replace(" ", "");
        Claims claims = jwtTokenUtil.parseJWT(res);

        String subject = claims.getSubject();

        JSONObject jsonObject = JSON.parseObject(subject);

        User user = JSON.toJavaObject(jsonObject, User.class);

        return login(user.getPhone(), user.getPassword());
    }

    @PostMapping("/update")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/follow")
    public String follow(@RequestParam("sub") Integer sub, @RequestParam("obj") Integer obj,
                    @RequestParam("flag") Integer flag) {

        if (Objects.equals(sub, obj)) {
            return "self";
        }

        // 1 means follow and -1 means un follow
        if (flag == 1 || flag == -1) {
            userService.follow_relation(sub, obj, flag);
            return "success";
        }

        else
            return "flag";
    }

    @PostMapping("/auth")
    public String auth(@RequestParam("sub") Integer sub, @RequestParam("obj") Integer obj,
                       @RequestParam("tar") Integer target) {
        if (target < -8 || target >= 8)
            return "target error";

        if (Objects.equals(sub, obj))
            return "self";

        return userService.auth(sub, obj, target);
    }

    @RequestMapping("/parsejwt")
    public void parseJwt(@RequestParam("token") String token) throws Exception {
        //String jwt = request.getParameter("token");
        if(token == null) return ;

        String res = token.replace(" ", "");
        Claims claims = jwtTokenUtil.parseJWT(res);

        String subject = claims.getSubject();

        JSONObject jsonObject = JSON.parseObject(subject);

        User user = JSON.toJavaObject(jsonObject, User.class);

        System.out.print(user);

    }
}
