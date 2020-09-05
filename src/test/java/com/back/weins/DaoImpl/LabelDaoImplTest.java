package com.back.weins.DaoImpl;

import com.back.weins.Dao.LabelDao;
import com.back.weins.entity.Label;
import com.back.weins.repository.LabelRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class LabelDaoImplTest {
    @Test
    private void contextLoads() {}

    @Autowired
    private LabelDao labelDao;

    @MockBean
    private LabelRepository labelRepository;

    @BeforeEach
    private void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    private void tearDown() {
        System.out.println("tear down");
    }

    @DisplayName("测试save和deleteById/Content")
    @Test
    void insert() {
        List<String> contents = new ArrayList<String>();
        contents.add("科技");
        contents.add("动漫");
        contents.add("科幻");
        contents.add("动作片");
        contents.add("娱乐");
        contents.add("IT");

        for (Integer index = 0; index < contents.size(); ++index) {
            Label label = new Label(index + 1, contents.get(index));
            labelDao.save(label);
        }

        List<Label> labels = new ArrayList<Label>();

        when(labelRepository.findAll()).thenReturn(labels);
        assertEquals(labels, labelDao.getLabels());

        labelDao.deleteByContent("娱乐");
        labelDao.deleteById(6);

        when(labelRepository.findAll()).thenReturn(labels);
        assertEquals(labels, labelDao.getLabels());
    }
}
