package com.back.weins.DaoImpl;

import com.back.weins.entity.Label;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LabelDaoImplTest {
    @Autowired
    private LabelDaoImpl labelDao;

    @Test
    void insert() {
        Label label = new Label();
        label.setContent("林旭亚纶");
        System.out.println(label);
        labelDao.save(label);
    }
}
