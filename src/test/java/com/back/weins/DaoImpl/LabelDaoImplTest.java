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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LabelDaoImplTest {
    @Test
    public void contextLoads() {}

    @Autowired
    private LabelDao labelDao;

    @MockBean
    private LabelRepository labelRepository;

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tear down");
    }

    @DisplayName("保存和删除测试")
    @Test
    public void saveAndDel() {
        List<String> contents = new ArrayList<String>();
        contents.add("科技");
        contents.add("动漫");
        contents.add("科幻");
        contents.add("动作片");
        contents.add("娱乐");
        contents.add("科技");

        for (String content : contents) {
            Label label = new Label();
            label.setContent(content);
            labelDao.save(label);
        }

        List<Label> labels = new ArrayList<Label>();
        when(labelRepository.findAll()).thenReturn(labels);
        assertEquals(labels, labelDao.getLabels());

        assertNull(labelDao.getById(6));

        Label expect = new Label();
        labelDao.deleteByContent("科技");
        when(labelRepository.findByContent("科技")).thenReturn(expect);
        assertEquals(expect, labelDao.getByContent("科技"));

        Label existAndDeleteLabel = new Label();
        existAndDeleteLabel.setContent("科技");
        labelDao.save(existAndDeleteLabel);
        assertEquals(expect, labelDao.getByContent("科技"));

        labelDao.deleteById(1);
        when(labelRepository.getOne(1)).thenReturn(expect);
        assertEquals(expect, labelDao.getById(1));

    }

    @DisplayName("查询测试")
    @Test
    public void find() {
        List<String> contents = new ArrayList<String>();
        contents.add("科技");
        contents.add("动漫");
        contents.add("科幻");
        contents.add("动作片");
        contents.add("娱乐");
        contents.add("科技");

        for (int index = 0; index < contents.size(); ++index) {
            Label label = new Label(index + 1, contents.get(index));
            labelDao.save(label);
        }

        List<Label> labels = new ArrayList<Label>();

        // 全部查询
        when(labelRepository.findAll()).thenReturn(labels);
        assertEquals(labels, labelDao.getLabels());
        // 模糊查询
        when(labelRepository.findPuzzy("科")).thenReturn(labels);
        assertEquals(labels, labelDao.findLabels("科"));

        Label label1 = new Label(1, "科技");
        // 根据id查询
        when(labelRepository.getOne(1)).thenReturn(label1);
        assertEquals(label1, labelDao.getById(1));
        // 根据内容查询
        when(labelRepository.findByContent("科技")).thenReturn(label1);
        assertEquals(label1, labelDao.getByContent("科技"));
    }
}
