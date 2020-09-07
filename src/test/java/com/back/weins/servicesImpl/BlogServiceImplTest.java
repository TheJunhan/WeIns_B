package com.back.weins.servicesImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.DaoImpl.BlogDaoImpl;
import com.back.weins.DaoImpl.LabelDaoImpl;
import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.entity.Label;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.services.BlogService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceImplTest {

    @Test
    public void contextLoads() {}

    @Autowired
    private BlogService blogService;

    @MockBean
    private BlogDaoImpl blogDao;

    @MockBean
    private LabelDaoImpl labelDao;

    @MockBean
    private UserDaoImpl userDao;

    private final String avatar = "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg";

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tear down");
    }

    private User getUser(Integer id, String name, String pass, String phone, Integer sex, Integer type) {
        User user = new User();
        if (id != -10) user.setId(id);

        user.setName(name);
        user.setPassword(pass);
        user.setPhone(phone);
        user.setSex(sex);
        user.setType(type);
        user.setBirthday("2000-01-01");
        user.setReg_time("2020-09-01 08:00:00");

        UserMongo userMongo = new UserMongo();
        if (id != -10) userMongo.setId(id);
        userMongo.setAvatar(avatar);

        user.setUserMongo(userMongo);
        return user;
    }

    @Test
    public void setTest() {
        String setLabelRes = "";
        Label label = new Label();
        label.setContent("CHINA!");
        when(labelDao.save(label)).thenReturn(setLabelRes);
        assertEquals(setLabelRes, blogService.setLabel("CHINA!"));

        Integer blogId = 1;
        String imag = "[]";
        String labelStr = "[{\"content\":\"IT\",\"id\":11}]";

        List<String> images = JSON.parseArray(imag, String.class);
        List<Label> labels = JSON.parseArray(labelStr, Label.class);
        when(blogDao.setBlog(1, 3, "hello world",
                "2020-09-01 08:00:00", "video", images, labels))
                .thenReturn(blogId);
        assertEquals(blogId, blogService.setBlog(1, 3, "hello world",
                "2020-09-01 08:00:00", "video", imag, labelStr,
                getUser(1, "Zangby", "111111", "11111111111", 1, 0)));

        Boolean expect = true;
        when(blogDao.setReblog(1, 1, 3, "hello world",
                "2020-09-01 08:00:00")).thenReturn(expect);
        assertEquals(expect, blogService.setReblog(1, 1, 3, "hello world",
                "2020-09-01 08:00:00"));
        when(blogDao.removeBlog(1, 1, 0)).thenReturn(expect);
        assertEquals(expect, blogService.removeBlog(1, 1, 0));
        when(blogDao.changeBlog(1, 1, "I disagree with you", 0)).thenReturn(expect);
        assertEquals(expect, blogService.changeBlog(1, 1, "I disagree with you", 0));
    }

    @Test
    public void getBlogTest() {
        JSONObject blog = new JSONObject();
        when(blogDao.getSingleBlog(1)).thenReturn(blog);
        assertEquals(blog, blogService.getSingleBlog(1));

        List<JSONObject> expect = new ArrayList<JSONObject>();

        when(blogDao.getPublicBlog()).thenReturn(expect);
        assertEquals(expect, blogService.getPublicBlog());
        when(blogDao.getPublicBlog_page(0, 5)).thenReturn(expect);
        assertEquals(expect, blogService.getPublicBlog_page(0, 5));

        when(blogDao.getBlogsByLabel(1, 1, null)).thenReturn(expect);
        assertEquals(expect, blogService.getBlogsByLabel(1, 1));
        when(blogDao.getBlogsByLabel_page(1, 1, 0, 5)).thenReturn(expect);
        assertEquals(expect, blogService.getBlogsByLabel_page(1, 1, 0, 5));

        when(blogDao.getBlogsLogined(1, null)).thenReturn(expect);
        assertEquals(expect, blogService.getBlogsLogined(1));
        when(blogDao.getBlogsLogined_page(1, 0, 5)).thenReturn(expect);
        assertEquals(expect, blogService.getBlogsLogined_page(1, 0, 5));

        when(blogDao.recommend(1, 0, 5)).thenReturn(expect);
        assertEquals(expect, blogService.recommend(1, 0, 5));
        when(blogDao.recommend_notLogin(0, 5)).thenReturn(expect);
        assertEquals(expect, blogService.recommend_notLogin(0, 5));

        when(blogDao.getBlogsById(1, 2)).thenReturn(expect);
        assertEquals(expect, blogService.getBlogsById(1, 2));
    }

    @Test
    public void counterTest() {
        Boolean expect = true;

        when(blogDao.setComment(1, 2, 1, "I agree with you", "2020-09-01 08:00:00", -1, -1)).thenReturn(expect);
        assertEquals(expect, blogService.setComment(1, 2, 1, "I agree with you", "2020-09-01 08:00:00", -1, -1));
        when(blogDao.removeComment(1, 1, 0)).thenReturn(expect);
        assertEquals(expect, blogService.removeComment(1, 1, 0));

        when(blogDao.setLike(1, 1)).thenReturn(expect);
        assertEquals(expect, blogService.setLike(1, 1));
        when(blogDao.removeLike(1, 1)).thenReturn(expect);
        assertEquals(expect, blogService.removeLike(1, 1));

        when(blogDao.setCollect(1, 1, true)).thenReturn(expect);
        assertEquals(expect, blogService.setCollect(1, 1, true));
        when(blogDao.setCollect(1, 1, false)).thenReturn(expect);
        assertEquals(expect, blogService.setCollect(1, 1, false));
    }

    @Test
    public void getLabelTest() {
        List<Label> labels = new ArrayList<Label>();

        when(labelDao.getLabels()).thenReturn(labels);
        assertEquals(labels, blogService.getLabels());
        when(labelDao.findLabels("CH")).thenReturn(labels);
        assertEquals(labels, blogService.findLabels("CH"));
    }
}
