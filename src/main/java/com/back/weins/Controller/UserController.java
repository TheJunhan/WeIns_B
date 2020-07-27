package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.JwtTokenUtil;
import com.back.weins.entity.User;
import com.back.weins.servicesImpl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

//    private final UserServiceImpl userService = new UserServiceImpl();
    @Autowired
    UserServiceImpl userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/getOne")
//    @PreAuthorize("hasRole('from_website')")
    public User getOne(@RequestParam("id") Integer id) {
        return userService.getByID(id);
    }

    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('from_website')")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("/reg")
//    @PreAuthorize("hasRole('from_website')")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
//    @PreAuthorize("hasRole('from_website')")
    public User login(@RequestParam("ph") String phone, @RequestParam("pwd") String password){
        return userService.login(phone, password);
    }

    @PostMapping("/update")
//    @PreAuthorize("hasRole('from_website')")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }
//    @PostMapping("/update")
////    @PreAuthorize("hasRole('from_website')")
//    public String update(@RequestParam("token") String token) throws Exception {
//        //if(token == null) return "token 无效";
//       System.out.print("收到请求");
//        //String res = token.replace(" ", "");
//        Claims claims = jwtTokenUtil.parseJWT(token);
//
//        String subject = claims.getSubject();
//
//        JSONObject jsonObject = JSON.parseObject(subject);
//
//        User user1 = JSON.toJavaObject(jsonObject, User.class);
//        System.out.print(user1);
//        return "ok";
//        //return userService.update(user);
//    }

    @PostMapping("/follow")
//    @PreAuthorize("hasRole('from_website')")
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
//    @PreAuthorize("hasRole('from_website')")
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
