package com.back.weins.DaoImpl;

import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.repository.UserMongoRepository;
import com.back.weins.repository.UserRepository;
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
class UserDaoImplTest {
    @Test
    public void contextLoads() {}

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMongoRepository userMongoRepository;

    @Autowired
    private UserDaoImpl userDao;

    @BeforeEach
    public void start() {
        System.out.println("test start");
    }

    @AfterEach
    public void end() {
        System.out.println("test end");
    }

    @DisplayName("一次性测试所有函数")
    @Test
    public void OneTest() {
        String avatar = "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg";

        User user = new User();
        user.setId(1);
        user.setName("臧斌宇");
        user.setPhone("11111111111");
        user.setBirthday("1938-03-14");
        user.setPassword("111111");
        user.setReg_time("2020-07-15 14:00:00");
        user.setSex(0);
        user.setType(0);

        UserMongo userMongo = new UserMongo();
        userMongo.setAvatar(avatar);

        user.setUserMongo(userMongo);
        userDao.save(user);

        User expect = new User();
        when(userRepository.findByName("臧斌宇")).thenReturn(expect);
        assertEquals(expect, userDao.getByName("臧斌宇"));

        when(userRepository.getOne(1)).thenReturn(expect);
        assertEquals(expect, userDao.getOne(1));

        // update and find by phone test
        user.setSex(1);
        userDao.update(user);
        when(userRepository.findByPhone("11111111111")).thenReturn(expect);
        assertEquals(expect, userDao.getByPhone("11111111111"));

        List<User> users = new ArrayList<User>();
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, userDao.getAll());

        when(userRepository.getByFuzzyName("臧")).thenReturn(users);
        assertEquals(users, userDao.getByFuzzyName("臧"));

        userDao.delete(1);
        assertEquals(expect, userDao.getOne(1));
    }
}
