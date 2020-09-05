package com.back.weins.DaoImpl;

import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.WeinsApplicationTests;
import com.back.weins.repository.LabelAndBlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LabelAndDaoImplTest extends WeinsApplicationTests {

    @Test
    private void contextLoads() {}

    @Autowired
    private LabelAndBlogDao labelAndBlogDao;

    @MockBean
    private LabelAndBlogRepository labelAndBlogRepository;

    @BeforeEach
    void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tear down");
    }

    @Test
    void setLAB() {
        labelAndBlogDao.setLAB(1, 1);
        List<Integer> blogs = new ArrayList<Integer>();
        when(labelAndBlogRepository.findByLid(1)).thenReturn(blogs);
        assertEquals(blogs, labelAndBlogDao.getByLid(1));
    }
}
