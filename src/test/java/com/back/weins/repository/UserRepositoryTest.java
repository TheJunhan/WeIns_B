package com.back.weins.repository;

import com.back.weins.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
//    @Autowired
//    UserRepository userRepository;
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
//    void insertOne() {
//        User user = new User();
//        user.setId(3);
//        user.setName("敖宇晨");
//        user.setBirthday("2001-07-25");
//        user.setPhone("15972777067");
//        user.setPassword("111111");
//        user.setSex(1);
//        user.setType(2);
//        userRepository.save(user);
//    }
//
//    // insert one test passed
//
//    @Test
//    void findOne() {
//        assertEquals(userRepository.findById(1).get().getName(), "徐珺涵");
//    }
//
//    // find one test passed
//
//    @Test
//    void otherFind() {
//        assertEquals(userRepository.findByPhone("15972777067").getName(), "敖宇晨");
//    }
//
//    // find by phone test passed
//
//    @Test
//    void updateOne() {
//        User user = userRepository.findById(3).get();
//        user.setType(3);
//        userRepository.save(user);
//
//        assertEquals(userRepository.findById(3).get().getType().toString(), "3");
//    }

    // update one test passed
}
