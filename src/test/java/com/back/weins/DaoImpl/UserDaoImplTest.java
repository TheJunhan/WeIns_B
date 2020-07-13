package com.back.weins.DaoImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDaoImplTest {
    @Autowired
    private UserDaoImpl userDao;

    @Test
    void findOne() {
        System.out.println(userDao.getOne(1));
    }
}
