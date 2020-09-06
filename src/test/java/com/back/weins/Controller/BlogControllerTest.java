package com.back.weins.Controller;

import com.alibaba.fastjson.JSON;
import com.back.weins.Utils.RequestUtils.BlogUtil;
import com.back.weins.Utils.RequestUtils.CommentUtils;
import com.back.weins.Utils.RequestUtils.ReblogUtil;
import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.entity.Label;

import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogControllerTest {

    @Autowired
    private BlogController blogController;

    @Autowired
    private UserController userController;
//
//    private MockMvc mockMvc;
//
    @Test
    public void testSetBlog() throws Exception {
//        List<String> image = new ArrayList<String>();
//        image.add("default");
//        image.add("default");
//        image.add("default");
//        image.add("default");
//        String ima = JSON.toJSONString(image);
//        List<Label> labe = new ArrayList<Label>();
//        Label la = new Label();
//        la.setId(5);
//        la.setContent("美食");
//        la.setFlag(0);
//        labe.add(la);
//        Label la1 = new Label();
//        la1.setId(7);
//        la1.setContent("运动");
//        la1.setFlag(0);
//        labe.add(la1);
//        String lab = JSON.toJSONString(labe);
//        BlogUtil blogUtil = new BlogUtil(1, 3, "自己可见：今天天气很晴朗，鸟儿生生唱", "2020-7-15", "null", ima,
//                lab, "敖宇晨");
//        Integer t = blogController.setBlog(blogUtil);
        System.out.println("cao");

    }
//
//    @Test
//    public void testGetBlog(){
////        System.out.print(blogController.getPublicBlogs());
//        ReblogUtil reblogUtil = new ReblogUtil(2, 3, 3, "开心", "2020-7-27", "疾风剑豪");
//        blogController.setReblog(reblogUtil);
//    }

//
//    @Test
//    public void testGetBlogsByLabel(){
//        //userController.follow(2, 1, -1);
//        System.out.print(blogController.getBlogsByLabel(5, 1).size());
//    }
    @Test
    public void testGetBlogsLogined(){
//        userController.follow(1, 2, 1);
//        userController.follow(3, 1, 1);
//        userController.follow(3, 2, 1);
//        User user = new User();
//        user.setName("徐珺涵");
//        user.setPhone("15044341612");
//        user.setBirthday("1911-03-14");
//        user.setPassword("111111");
//        user.setReg_time("2020-07-21 14:00:00");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        user.setUserMongo(userMongo);
//        userController.register(user);
//        List<Integer> tmp = new ArrayList<>();
//        tmp.add(1);
//        tmp.add(2);
//        RegisterUtil registerUtil = new RegisterUtil(-1,
//                "1",
//                "111111",
//                "15044341612",
//                "2000-07-01",
//                "?",
//                0,
//                tmp);
//        userController.register(registerUtil);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        System.out.println(map.get(4));
//        System.out.print((blogController.getBlogsLogined(3)).size());
    }
//
//    @Test
//    public void setLikeTest(){
//        blogController.setLike(2, 4);
//        blogController.setLike(3, 4);
//    }
//
//    @Test
//    public void removeLikeTest(){
//        blogController.removeLike(2, 4);
//    }
//
//    @Test
//    public void removeBlogTest(){
//        blogController.removeBlog(1, 4, 0);
//    }
//
//    @Test
//    public void setReblog(){
//
//    }
//
//    @Test
//    public void setCommentTest(){
//        CommentUtils commentUtils = new CommentUtils(2, 1, "2020-7-29", 1, -1, "第二条评论！！");
//
//        blogController.setComment(commentUtils);
//    }
//
//    @Test
//    public void setCollect(){
////        blogController.setCollect(3, 3, true);
//        blogController.setCollect(3, 3, false);
//    }



}
