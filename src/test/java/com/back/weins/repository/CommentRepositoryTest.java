package com.back.weins.repository;

import com.back.weins.WeinsApplicationTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest extends WeinsApplicationTests {
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        System.out.println("test start");
    }

    @AfterEach
    void tearDown() {
        System.out.println("test end");
    }

    @Test
    void findByTo_cid() {
        System.out.println(commentRepository.findByTo_cid(-1));
        System.out.println(commentRepository.findByTo_cid(0));
    }

    @Test
    void findByBid() {
        System.out.println(commentRepository.findByBid(14));
    }
}
