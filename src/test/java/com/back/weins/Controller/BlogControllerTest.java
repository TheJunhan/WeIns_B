package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.back.weins.entity.Label;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogControllerTest {

    @Autowired
    private BlogController blogController;

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    public void testSetBlog() throws Exception {
        List<String> image = new ArrayList<String>();
        image.add("default");
        image.add("default");
        image.add("default");
        image.add("default");
        String ima = JSON.toJSONString(image);
        List<Label> labe = new ArrayList<Label>();
        Label la = new Label();
        la.setId(5);
        la.setContent("美食");
        la.setFlag(0);
        labe.add(la);
        Label la1 = new Label();
        la1.setId(7);
        la1.setContent("运动");
        la1.setFlag(0);
        labe.add(la1);
        String lab = JSON.toJSONString(labe);
        Integer t = blogController.setBlog(1, 0, "2020-7-15", "null", ima,
                lab, "徐珺涵", "default");

    }

    @Test
    public void testGetBlog(){
        System.out.print(blogController.getPublicBlogs());
    }


    @Test
    public void testGetBlogsByLabel(){
        //userController.follow(2, 1, -1);
        System.out.print(blogController.getBlogsByLabel(5, 1));
    }
    @Test
    public void testGetBlogsLogined(){
//        userController.follow(2, 1, 1);
//        User user = new User();
//        user.setName("敖宇晨");
//        user.setPhone("110");
//        user.setBirthday("1911-03-14");
//        user.setPassword("111111");
//        user.setReg_time("2020-07-15 14:00:00");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        user.setUserMongo(userMongo);
//        userController.register(user);
        System.out.print((blogController.getBlogsLogined(1)).size());
    }

    @Test
    public void setLikeTest(){
        blogController.setLike(1, 5);
    }

}
