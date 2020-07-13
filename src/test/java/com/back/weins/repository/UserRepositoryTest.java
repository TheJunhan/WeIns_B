package com.back.weins.repository;

import com.back.weins.entity.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private com.back.weins.repository.userRepository userRepository;

    @Test
    void insertOne() {
        user user = new user();
        user.setUid(3);
        user.setName("ayc");
        user.setBirthday("2001-07-25");
        user.setPhone("15972777067");
        user.setPassword("111111");
        user.setSex(1);
        user.setType(2);
        userRepository.save(user);
        System.out.println("insert one test passed!");
    }

    @Test
    void findOne() {
        System.out.println(userRepository.findById(3));
        System.out.println("find one test passed!");
    }

    @Test
    void otherFind() {
        System.out.println(userRepository.findByPhone("15972777067"));
        System.out.println("find by phone test passed!");
    }

    @Test
    void updateOne() {
        user user = userRepository.findById(3).get();
        user.setType(3);
        userRepository.save(user);
        System.out.println("update one test passed!");
    }
}
