package com.back.weins.Controller;

import com.back.weins.WeinsApplicationTests;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest extends WeinsApplicationTests {
    @Autowired
    private UserController userController;

    @Test
    public void loginTest() throws Exception {
        System.out.print(userController.login("15044341612", "111111"));
    }

    @Test
    public void parseTest() throws Exception {
        userController.parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ3ZWlucy0yMDIwIiwiaWF0IjoxNTk1NTY5MDI5LCJzdWIiOiJ7XCJiaXJ0aGRheVwiOlwiMTkxMS0wMy0xNFwiLFwiaWRcIjoyLFwibmFtZVwiOlwi5b6Q54-65ra1XCIsXCJwYXNzd29yZFwiOlwiMTExMTExXCIsXCJwaG9uZVwiOlwiMTUwNDQzNDE2MTJcIixcInJlZ190aW1lXCI6XCIyMDIwLTA3LTIxIDA0OjE5OjA2XCIsXCJzZXhcIjowLFwidHlwZVwiOjAsXCJ1c2VyTW9uZ29cIjp7XCJhdmF0YXJcIjpcImh0dHA6Ly9icGljLjU4OGt1LmNvbS9lbGVtZW50X3BpYy8wMS81NS8wOS82MzU3NDc0ZGJmMjQwOWMuanBnXCIsXCJibG9nX251bVwiOjAsXCJibG9nc1wiOltdLFwiY29sbGVjdF9ibG9nXCI6W10sXCJjb21tZW50X2Jsb2dcIjpbMV0sXCJjb21tZW50c1wiOlszXSxcImZvbGxvd2VyX251bVwiOjAsXCJmb2xsb3dlcnNcIjpbXSxcImZvbGxvd2luZ19udW1cIjowLFwiZm9sbG93aW5nc1wiOltdLFwiaWRcIjoyLFwibGlrZV9ibG9nXCI6W119fSIsImV4cCI6MTU5NTU3MjYyOX0.c2wDWAleYnTbpxCoaSd3v2EZB6WvMKnMyGV2EVllywU");

    }
//
//    @BeforeEach
//    public void setUp() {
//        System.out.println("test start");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        System.out.println("test end");
//    }
//
//    @Test
//    public void getOne() {
//        assertEquals("徐珺涵", userController.getOne(1).getName());
//        assertEquals("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg",
//                userController.getOne(2).getUserMongo().getAvatar());
//    }
//
//    @Test
//    public void getAll() {
//        List<User> users = userController.getAll();
//
//        assertEquals("徐珺涵", users.get(0).getName());
//        assertEquals("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg",
//                users.get(1).getUserMongo().getAvatar());
//    }
//
//    @Test
//    public void register() {
//        User user = new User();
//        user.setName("徐珺涵");
//        user.setPhone("15972777067");
//        user.setBirthday("1891-03-14");
//        user.setPassword("111111");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        user.setUserMongo(userMongo);
//
//        // phone exists
//        Assert.assertEquals("phone error", userController.register(user));
//
//        user.setPhone("15977777277");
//
//        // name exists
//        Assert.assertEquals("name error", userController.register(user));
//
//        user.setName("陈志立");
//
//        // success
//        Assert.assertEquals("success", userController.register(user));
//    }
//
//    @Test
//    public void login() {
//        // there is no this account
//        Assert.assertEquals("-1", userController.login("11111111111", "123456").getId().toString());
//
//        // password wrong
//        Assert.assertEquals("-2", userController.login("15972777067", "123456").getId().toString());
//
//        // success
//        Assert.assertEquals("3", userController.login("15972777067", "111111").getId().toString());
//
//        // expect password mask to keep security
//        assertNull(userController.login("15972777067", "111111").getPassword());
//    }
//
//    @Test
//    public void update() {
//        User user = userController.getOne(1);
//        user.setPassword("111111");
//        userController.update(user);
//
//        assertEquals("111111", userController.getOne(1).getPassword());
//    }
//
//    @Test
//    public void follow() {
//        assertEquals("flag", userController.follow(1, 2, 2));
//
//        assertEquals("self", userController.follow(1, 1, 1));
//
//        assertEquals("success", userController.follow(2, 3, 1));
//
//        assertEquals("success", userController.follow(2, 3, -1));
//    }
//
//    @Test
//    public void auth() {
//        assertEquals("self", userController.auth(2, 2, 3));
//        assertEquals("target error", userController.auth(2, 2, 8));
//        assertEquals("target equals", userController.auth(1, 2, 7));
//        assertEquals("obj is boss", userController.auth(2, 1, 4));
//        assertEquals("sub not boss", userController.auth(21, 2, 0));
//        assertEquals("sub not boss", userController.auth(21, 3, 5));
//        assertEquals("sub not admin", userController.auth(3, 24 ,-8));
//        assertEquals("success", userController.auth(1, 22, -7));
//        assertEquals("success", userController.auth(1, 24, -8));
//    }
}

