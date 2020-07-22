package com.back.weins.DaoImpl;

import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserDaoImplTest {
//    @Autowired
//    private UserDaoImpl userDao;
//
//    @BeforeEach
//    public void start() {
//        System.out.println("test start");
//    }
//
//    @AfterEach
//    public void end() {
//        System.out.println("test end");
//    }
//
//    @Test
//    public void getAll() {
//        List<User> users = userDao.getAll();
//        assertEquals(users.get(0).getName(), "徐珺涵");
//        assertEquals(users.get(1).getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void findById() {
//        assertEquals(userDao.getOne(1).getName(), "徐珺涵");
//        assertEquals(userDao.getOne(1).getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void findByName() {
//        assertEquals(userDao.getByName("敖宇晨").getPhone(), "15972777067");
//        assertEquals(userDao.getByName("敖宇晨").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void findByPhone() {
//        assertEquals(userDao.getByPhone("15972777067").getName(), "敖宇晨");
//        assertEquals(userDao.getByPhone("15972777067").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    void save() {
//        User user = new User();
//        user.setName("臧斌宇");
//        user.setPhone("12501250250");
//        user.setBirthday("1938-03-14");
//        user.setPassword("111111");
//        user.setReg_time("2020-07-15 14:00:00");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//
//        user.setUserMongo(userMongo);
//        userDao.save(user);
//        assertEquals(userDao.getByName("臧斌宇").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void update() {
//        String reg_time = "2020-07-12 08:00:00";
//        User user = userDao.getOne(4);
//        user.setReg_time(reg_time);
//        userDao.update(user);
//        assertEquals(userDao.getOne(4).getReg_time(), reg_time);
//    }
//
//    @Test
//    public void delete() {
//        userDao.delete(20);
//    }
}
