package com.back.weins.DaoImpl;

import com.back.weins.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class UserDaoImplTest {
    @Autowired
    private UserDaoImpl userDao;

    @Test
    void findOne() {
        System.out.println(userDao.getOne(1));
    }

    @Test
    void save() {

    }

    @Test
    void update() {
        String reg_time = "2020-07-10 08:00:00";
        User user = userDao.getOne(3);
        user.setReg_time(reg_time);
        userDao.update(user);
        if (Objects.equals(userDao.getOne(1).getReg_time(), reg_time)) {
            System.out.println("update test passed");
        }

        else
            System.out.println("update test failed");
    }
}
