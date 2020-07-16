package com.back.weins.Controller;

import com.back.weins.entity.User;
import com.back.weins.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
}
