package com.back.weins.repository;

import com.back.weins.entity.Label;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class LabelRepositoryTest {
//    @Autowired
//    private LabelRepository labelRepository;
//
//    @BeforeEach
//    void setUp() {
//        System.out.println("Begin test");
//    }
//
//    @AfterEach
//    void tearDown() {
//        System.out.println("End test");
//    }
//
//    @Test
//    void insert() {
//        Label label = new Label(1, "徐珺涵");
//        labelRepository.save(label);
//        assertEquals(label, labelRepository.findByContent("徐珺涵"));
//
//        Label label1 = new Label();
//        label1.setContent("任锐");
//        labelRepository.save(label1);
//        assertEquals(label1, labelRepository.findByContent("任锐"));
//    }
//
//    // update, save and find test passed
//
//    /*
//     *  delete is unnecessary because we do not really delete a tuple,
//     *  just set a flag to show whether it is deleted
//     */
}
