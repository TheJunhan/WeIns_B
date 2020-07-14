package com.back.weins.repository;

import com.back.weins.entity.Label;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LabelRepositoryTest {
    @Autowired
    private LabelRepository labelRepository;

    @Test
    void insert() {
        Label label = new Label(1, "徐珺涵");
        labelRepository.save(label);

        Label label1 = new Label();
        label1.setContent("付玉晗");
        labelRepository.save(label1);
    }

    // update and save passed

    @Test
    void delete() {
        labelRepository.deleteById(2);
    }

    // delete passed

    @Test
    void findByContent() {
        System.out.println(labelRepository.findByContent("付玉晗"));
        System.out.println(labelRepository.findByContent("敖宇晨"));
    }

    // find by content passed
}
