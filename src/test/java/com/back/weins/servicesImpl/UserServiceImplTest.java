package com.back.weins.servicesImpl;

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
        if (id != -10) user.setId(id);

        user.setName(name);
        user.setPassword(pass);
        user.setPhone(phone);
        user.setSex(sex);
        user.setType(type);
        user.setBirthday("2000-01-01");
        user.setReg_time("2020-09-01 08:00:00");

        UserMongo userMongo = new UserMongo();
        if (id != -10) userMongo.setId(id);
        userMongo.setAvatar(avatar);

        user.setUserMongo(userMongo);
        return user;
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++)
            users.add(getUser(i, "臧斌宇" + Integer.toString(i), "111111", "1111111111" + Integer.toString(i), -1, 0));

        return users;
    }

    @Test
    public void save() {
        for (User user : getUsers())
            userService.save(user);

        List<User> users = new ArrayList<User>();
        when(userDao.getAll()).thenReturn(users);
        assertEquals(users, userService.getAll());

        when(userDao.getByFuzzyName("臧")).thenReturn(users);
        assertEquals(users, userService.getByFuzzyName("臧"));

        User expect = new User();
        when(userDao.getOne(1)).thenReturn(expect);
        assertEquals(expect, userService.getByID(1));

        userService.delete(1);
        assertEquals(expect, userService.getByID(1));

        when(userDao.getByName("臧斌宇1")).thenReturn(expect);
        assertEquals(expect, userService.getByName("臧斌宇1"));

        when(userDao.getByPhone("11111111110")).thenReturn(expect);
        assertEquals(expect, userService.getByPhone("11111111110"));
    }

    @Test
    public void update() {
        List<User> users = getUsers();
        for (User user: users)
            userService.save(user);

        User user = users.get(0);
        User user1 = users.get(1);
        User user2 = users.get(2);
        user2.setId(-1); // useless user

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

        List<User> TestUtil1 = new ArrayList<User>();
        TestUtil1.add(user); // find by id, the same
        TestUtil1.add(user1); // find by name
        TestUtil1.add(user1); // find by phone
        assertEquals("error", userService.update(registerUtil, TestUtil1));
        System.out.println("name error");

        registerUtil.setName("臧斌宇99");

        List<User> TestUtil2 = new ArrayList<User>();
        TestUtil2.add(user); // find by id, the same
        TestUtil2.add(user2); // find by name is null
        TestUtil2.add(user1); // find by phone
        assertEquals("errorPhone", userService.update(registerUtil, TestUtil2));
        System.out.println("phone error");

        registerUtil.setPhone("11111111199");

        List<User> TestUtil3 = new ArrayList<User>();
        TestUtil3.add(user); // find by id, the same
        TestUtil3.add(user2); // find by name is null
        TestUtil3.add(user2); // find by phone is null
        assertEquals("success", userService.update(registerUtil, TestUtil3));
        System.out.println("success");
    }

    @Test
    public void register() {
        List<User> users = getUsers();
        for (User user: users)
            userService.save(user);

        User user1 = users.get(1);
        User user2 = users.get(2);
        user2.setId(-1); // useless user

        List<Integer> interests = new ArrayList<Integer>();
        interests.add(1);
        interests.add(2);
        interests.add(3);

        RegisterUtil registerUtil = new RegisterUtil(null,
                user1.getName(),
                user1.getPassword(),
                user1.getPhone(),
                user1.getBirthday(),
                user1.getUserMongo().getAvatar(),
                user1.getSex(),
                interests);

        List<User> TestUtil1 = new ArrayList<User>();
        TestUtil1.add(user1); // find by name
        TestUtil1.add(user1); // find by phone
        assertEquals("phone error", userService.register(registerUtil, TestUtil1));
        System.out.println("phone error");

        registerUtil.setPhone("11111111199");

        List<User> TestUtil2 = new ArrayList<User>();
        TestUtil2.add(user2); // find by phone is null
        TestUtil2.add(user1); // find by name
        assertEquals("name error", userService.register(registerUtil, TestUtil2));
        System.out.println("name error");

        registerUtil.setName("臧斌宇99");

        List<User> TestUtil3 = new ArrayList<User>();
        TestUtil3.add(user2); // find by phone is null
        TestUtil3.add(user2); // find by name is null
        assertEquals("success", userService.register(registerUtil, TestUtil3));
        System.out.println("success");
    }

    @Test
    public void login() {
        List<User> users = getUsers();
        for (User user : users)
            userService.save(user);

        User user1 = users.get(1); // 登录目标账号
        String phone = "11111111199"; // 不存在的号码
        String pwd = "1111111"; // 错误的密码

        User expect = new User();
        expect.setId(-1); // 意味着对应电话号码的账户不存在
        expect.setPassword(null);
        expect.setReg_time(null);
        expect.setName(null);
        expect.setBirthday(null);
        expect.setSex(null);
        expect.setType(null);
        expect.setPhone(null);
        expect.setUserMongo(null);

        user1.setId(-1);
        assertEquals(expect, userService.login(phone, pwd, user1));

        user1.setId(1);
        expect.setId(-2);
        phone = user1.getPhone();
        assertEquals(expect, userService.login(phone, pwd, user1));

        pwd = user1.getPassword();
        User expect1 = getUser(1,
                user1.getName(),
                "HFKJSHKFUUFHDKJhskajhkjhak21u3iuehfdskjhskjahdkuwduyfew",
                user1.getPhone(),
                user1.getSex(),
                user1.getType());

        assertEquals(expect1, userService.login(phone, pwd, user1));
    }

    @Test
    public void followAndAuth() {
        List<User> users = getUsers();
        for (User user : users)
            userService.save(user);

        List<User> Test = new ArrayList<User>();
        User user1 = users.get(1);
        User user2 = users.get(2);
        Test.add(user1);
        Test.add(user2);

        userService.follow_relation(1, 2, 1, Test);
        userService.follow_relation(1, 2, -1, Test);

        assertEquals("target equals", userService.auth(1, 2, 0, Test));
        assertEquals("sub not admin", userService.auth(1, 2, 1, Test));

        user1.setType(1);
        user2.setType(8);
        assertEquals("obj is boss", userService.auth(1, 2, 0, Test));

        user2.setType(0);
        assertEquals("sub not boss", userService.auth(1, 2, 1, Test));
        assertEquals("sub no ban auth", userService.auth(1, 2, -8, Test));

        user2.setType(1);
        assertEquals("sub not boss", userService.auth(1, 2, 3, Test));

        user1.setType(8);
        assertEquals("success", userService.auth(1, 2, 3, Test));
    }
}
