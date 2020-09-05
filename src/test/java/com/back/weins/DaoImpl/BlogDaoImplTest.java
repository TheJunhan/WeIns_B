package com.back.weins.DaoImpl;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.WeinsApplicationTests;
import com.back.weins.entity.Blog;
import com.back.weins.entity.Label;
import com.back.weins.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BlogDaoImplTest extends WeinsApplicationTests {
    @Test
    public void contextLoads() {}

    @MockBean
    private BlogRepository blogRepository;

    @MockBean
    private BlogMongoRepository blogMongoRepository;

    @MockBean
    private LabelAndBlogDao labelAndBlogDao;

    @MockBean
    private LabelAndBlogRepository labelAndBlogRepository;

    @MockBean
    private UserMongoRepository userMongoRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LabelRepository labelRepository;


    @Autowired
    private BlogDao blogDao;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final String content = "hello world";
    private final String video = "video";

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    public void TearDown() {
        System.out.println("tear down");
    }

    private List<String> getImages() {
        List<String> images = new ArrayList<String>();
        return images;
    }

    private List<Label> getLabels() {
        List<Label> labels = new ArrayList<Label>();
        Label label1 = new Label(1, "科技");
        Label label2 = new Label(2, "军事");
        labels.add(label1);
        labels.add(label2);

        return labels;
    }

    private String getTime() {
        Date date = new Date();
        return format.format(date);
    }

    @Test
    public void save() {
        String time = getTime();
//        List<String> images = new ArrayList<String>();
//        List<Label> labels = getLabels();

        Blog blog = new Blog();
        blog.setUid(1);
        blog.setType(1);
        blog.setPost_day(time);

        Blog expectBlog = new Blog();
        when(blogRepository.save(blog)).thenReturn(expectBlog);
        assertEquals(expectBlog.getId(), blogDao.setBlog(1, 1, content, time, video, getImages(), getLabels()));
    }

    @Test
    public void MultiSave() {
        for (int i = 0; i < 10; ++i) {
            blogDao.setBlog(1, 1, content, getTime(), video, getImages(), getLabels());
        }
        List<JSONObject> expect = new ArrayList<JSONObject>();
        List<JSONObject> pubBlogs = blogDao.getPublicBlog();
    }
}
