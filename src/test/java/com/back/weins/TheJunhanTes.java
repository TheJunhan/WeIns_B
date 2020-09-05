package com.back.weins;

import com.back.weins.Controller.UserController;
import com.back.weins.DaoImpl.BlogDaoImpl;
import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TheJunhanTes {
    @Autowired
    BlogDaoImpl blogDao;

    @Autowired
    UserController userController;

    @Test
    public void test() {
//        Integer id;
//        String name;
//        String password;
//        String phone;
//        String birthday;
//        String avatar;
//        Integer sex;
//        List<Integer> interests = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        RegisterUtil registerUtil = new RegisterUtil(-1, "徐珺涵", "111111",
                "15144341612", "2000-07-01", "?", 0, tmp);
        userController.register(registerUtil);
        //blogDao.getBlogsById(1, 1);
    }
}
