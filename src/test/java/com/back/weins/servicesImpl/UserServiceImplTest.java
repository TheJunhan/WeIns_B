package com.back.weins.servicesImpl;

import com.back.weins.Dao.UserDao;
import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.WeinsApplicationTests;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest extends WeinsApplicationTests {
    @Test
    public void contextLoads() {}

    @MockBean
    private UserDaoImpl userDao;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        System.out.println("set up");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("tear down");
    }

    private final String avatar = "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg";

    private User getUser(Integer id, String name, String pass, String phone, Integer sex, Integer type) {
        User user = new User();
        if (id >= 0) user.setId(id);

        user.setName(name);
        user.setPassword(pass);
        user.setPhone(phone);
        user.setSex(sex);
        user.setType(type);
        user.setBirthday("2000-01-01");
        user.setReg_time("2020-09-01 08:00:00");

        UserMongo userMongo = new UserMongo();
        userMongo.setAvatar(avatar);

        user.setUserMongo(userMongo);
        return user;
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++)
            users.add(getUser(i, "臧斌宇" + Integer.toString(i), "111111", "1111111111" + Integer.toString(i), -1, 1));

        return users;
    }

    @Test
    public void save() {
        for (User user : getUsers()) {
            userService.save(user);
        }

        List<User> users = new ArrayList<User>();
        when(userDao.getAll()).thenReturn(users);
        assertEquals(users, userService.getAll());

        when(userDao.getByFuzzyName("臧")).thenReturn(users);
        assertEquals(users, userService.getByFuzzyName("臧"));

        User expect = new User();
        when(userDao.getOne(1)).thenReturn(expect);
        assertEquals(expect, userService.getByID(1));

        when(userDao.getByName("臧斌宇1")).thenReturn(expect);
        assertEquals(expect, userService.getByName("臧斌宇1"));

        when(userDao.getByPhone("11111111110")).thenReturn(expect);
        assertEquals(expect, userService.getByPhone("11111111110"));
    }

    @Test
    public void update() {

        List<User> users = getUsers();
        User user = users.get(0);
        User user1 = users.get(1);
        List<Integer> interests = new ArrayList<Integer>();
        interests.add(1);
        interests.add(2);
        interests.add(3);

        RegisterUtil registerUtil = new RegisterUtil(user.getId(),
                user1.getName(),
                user.getPassword(),
                user1.getPhone(),
                user.getBirthday(),
                user.getUserMongo().getAvatar(),
                user.getSex(),
                interests);

        String updateRes = "";

        List<User> TestUtil1 = new ArrayList<User>();
        TestUtil1.add(user); // find by id, the same
        TestUtil1.add(user1); // find by name
        TestUtil1.add(user1); // find by phone

        when(userService.update(any(), any())).thenReturn(updateRes);
        assertEquals("error", userService.update(registerUtil, TestUtil1));


        List<User> TestUtil2 = new ArrayList<User>();
        TestUtil1.add(user); // find by id, the same
        TestUtil1.add(null); // find by name
        TestUtil1.add(user1); // find by phone

        registerUtil.setName("臧斌宇99");
        when(userService.update(registerUtil, TestUtil2)).thenReturn(updateRes);
        assertEquals("errorPhone", userService.update(registerUtil, TestUtil2));


        List<User> TestUtil3 = new ArrayList<User>();
        TestUtil1.add(user); // find by id, the same
        TestUtil1.add(null); // find by name
        TestUtil1.add(null); // find by phone

        registerUtil.setPhone("11111111199");
        when(userService.update(registerUtil, TestUtil3)).thenReturn(updateRes);
        assertEquals("success", userService.update(registerUtil, TestUtil3));
    }

//    @Test
//    public void getByID() {
//        assertEquals(userService.getByID(1).getName(), "徐珺涵");
//        assertEquals(userService.getByID(2).getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void getByPhone() {
//        assertEquals(userService.getByPhone("15972777067").getName(), "敖宇晨");
//        assertEquals(userService.getByPhone("15972777067").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void getByName() {
//        assertEquals(userService.getByName("敖宇晨").getPhone(), "15972777067");
//        assertEquals(userService.getByName("敖宇晨").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void getAll() {
//        List<User> users = userService.getAll();
//        assertEquals(users.get(0).getName(), "徐珺涵");
//        assertEquals(users.get(1).getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void save() {
//        User user = new User();
//        user.setName("吕智国");
//        user.setPhone("12501290290");
//        user.setBirthday("1911-03-14");
//        user.setPassword("111111");
//        user.setReg_time("2020-07-15 14:00:00");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//
//        user.setUserMongo(userMongo);
//        userService.save(user);
//        assertEquals(userService.getByName("董明凯").getUserMongo().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    @Test
//    public void update() {
//        String reg_time = "2020-07-12 08:00:00";
//        User user = userService.getByID(5);
//        user.setReg_time(reg_time);
//        userService.update(user);
//        assertEquals(userService.getByID(5).getReg_time(), reg_time);
//    }
//
//    @Test
//    public void delete() {
//        userService.delete(5);
//    }
//
//    @Test
//    public void register() {
//        User user = new User();
//        user.setName("徐珺涵");
//        user.setPhone("15972777067");
//        user.setBirthday("1991-03-14");
//        user.setPassword("111111");
//        user.setSex(0);
//        user.setType(0);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        user.setUserMongo(userMongo);
//
//        // phone exists
//        assertEquals("phone error", userService.register(user));
//
//        user.setPhone("15970777277");
//
//        // name exists
//        assertEquals("name error", userService.register(user));
//
//        user.setName("吴侃真");
//
//        // success
//        assertEquals("success", userService.register(user));
//    }
//
//    @Test
//    public void login() {
//        // there is no this account
//        assertEquals("-1", userService.login("11111111111", "123456").getId().toString());
//
//        // password wrong
//        assertEquals("-2", userService.login("15972777067", "123456").getId().toString());
//
//        // success
//        assertEquals("3", userService.login("15972777067", "111111").getId().toString());
//
//        // expect password mask to keep security
//        assertNull(userService.login("15972777067", "111111").getPassword());
//    }
//
//    @Test
//    public void follow() {
//        // follow
//        userService.follow_relation(1, 2, 1);
//        userService.follow_relation(1, 3, 1);
//
//        assertEquals("2", userService.getByID(1).getUserMongo().getFollowing_num().toString());
//        assertEquals("1", userService.getByID(2).getUserMongo().getFollower_num().toString());
//        assertEquals("1", userService.getByID(3).getUserMongo().getFollower_num().toString());
//
//        List<Integer> followings1 = userService.getByID(1).getUserMongo().getFollowings();
//        List<Integer> followers1 = userService.getByID(2).getUserMongo().getFollowers();
//        List<Integer> followers2 = userService.getByID(3).getUserMongo().getFollowers();
//
//        assertEquals("2", followings1.get(0).toString());
//        assertEquals("3", followings1.get(1).toString());
//        assertEquals("1", followers1.get(0).toString());
//        assertEquals("1", followers2.get(0).toString());
//
//        // un follow
//        userService.follow_relation(1,2,-1);
//        assertEquals("1", userService.getByID(1).getUserMongo().getFollowing_num().toString());
//        assertEquals("0", userService.getByID(2).getUserMongo().getFollower_num().toString());
//
//        List<Integer> followings2 = userService.getByID(1).getUserMongo().getFollowings();
//
//        assertEquals("3", followings2.get(0).toString());
//    }
//
//    @Test
//    public void auth() {
//        assertEquals("target equals", userService.auth(1, 2, 7));
//        assertEquals("obj is boss", userService.auth(2, 1, 4));
//        assertEquals("sub not boss", userService.auth(21, 2, 0));
//        assertEquals("sub not boss", userService.auth(21, 3, 5));
//        assertEquals("sub not admin", userService.auth(3, 24 ,-8));
//        assertEquals("success", userService.auth(1, 22, -7));
//        assertEquals("success", userService.auth(1, 24, -8));
//    }
}
