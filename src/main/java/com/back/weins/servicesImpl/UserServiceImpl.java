package com.back.weins.servicesImpl;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Constant.Constant;
import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.Utils.JwtTokenUtil;
import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDaoImpl userDao;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public User getByID(Integer id){
        return userDao.getOne(id);
    }

    @Override
    public User getByName(String name){
        return userDao.getByName(name);
    }

    @Override
    public List<User> getByFuzzyName(String name) {
        return userDao.getByFuzzyName(name);
    }

    @Override
    public User getByPhone(String phone) {
        return userDao.getByPhone(phone);
    }

    @Override
    public List<User> getAll(){
        return userDao.getAll();
    }

    @Override
    public String save(User user){
        System.out.println(user);
        if (userDao.getByName(user.getName()) != null)
            return "error";
        userDao.save(user);
        return "success";
    }

    @Override
    public String update(RegisterUtil registerUtil, List<User> Test){
        Boolean flag = (Test != null);

        User res = flag ? Test.get(0) : userDao.getOne(registerUtil.getId());

        if (!Objects.equals(registerUtil.getName(), res.getName())) {
            User NameRes = flag ? Test.get(1) : userDao.getByName(registerUtil.getName());

            if (NameRes != null)
                return "error";
//            if (userDao.getByName(registerUtil.getName()) != null)
//                return "error";
        }

        if (!Objects.equals(registerUtil.getPhone(), res.getPhone())) {
            User PhoneRes = flag ? Test.get(2) : userDao.getByPhone(registerUtil.getPhone());

            if (PhoneRes != null)
                return "errorPhone";
//            if (userDao.getByPhone(registerUtil.getPhone()) != null)
//                return "errorPhone";
        }

        if (registerUtil.getName() != null)
            res.setName(registerUtil.getName());

        if (registerUtil.getPhone() != null)
            res.setPhone(registerUtil.getPhone());

        if (registerUtil.getPassword() != null)
            res.setPassword(registerUtil.getPassword());

        if (registerUtil.getBirthday() != null)
            res.setBirthday(registerUtil.getBirthday());

        if (registerUtil.getSex() != null)
            res.setSex(registerUtil.getSex());

        if (registerUtil.getAvatar() != null) {
            UserMongo userMongo = res.getUserMongo();
            userMongo.setAvatar(registerUtil.getAvatar());
            res.setUserMongo(userMongo);
        }

        userDao.update(res);
        return "success";
    }

    @Override
    public void delete(Integer id){
        userDao.delete(id);
    }

    @Override
    @Cacheable(value="users", key = "#registerUtil.phone")
    public String register(RegisterUtil registerUtil) {
        if (userDao.getByPhone(registerUtil.getPhone()) != null) {
            return "phone error";
        }

        if (userDao.getByName(registerUtil.getName()) != null) {
            return "name error";
        }

        User user = new User();
        user.setName(registerUtil.getName());
        user.setPassword(registerUtil.getPassword());
        user.setPhone(registerUtil.getPhone());
        user.setBirthday(registerUtil.getBirthday());

        UserMongo mongo = new UserMongo();
        Map<Integer, Integer> tmp = new HashMap<>();

        for(Integer i = 0; i < registerUtil.getInterests().size(); ++i) {
            tmp.put(registerUtil.getInterests().get(i), 5);
        }
        mongo.setInterests(tmp);
        mongo.setAvatar(registerUtil.getAvatar());
        user.setUserMongo(mongo);

        user.setSex(-1);
        user.setType(0);
        Date reg_time = new Date();
        user.setReg_time(format.format(reg_time));
        userDao.save(user);

        return "success";
    }

    private User userMask(Integer code) {
        User user = new User();
        user.setId(code);
        return user;
    }

    @Override
    @Cacheable(value="users", key = "#phone")
    public User login(String phone, String password){
        User user1 = userDao.getByPhone(phone);
        if (user1 == null) {
            return userMask(-1);
        }
        else if (!Objects.equals(user1.getPassword(), password)) {
            return userMask(-2);
        }
        User user2 = new User();
        user2.setPhone(user1.getPhone());
        user2.setPassword(user1.getPassword());
        user2.setId(user1.getId());
        String jwt = jwtTokenUtil.createJWT(Constant.JWT_ID, JwtTokenUtil.generealSubject(user2), Constant.JWT_TTL);
        user1.setPassword(jwt);

        return user1;
    }

    @Override
    public void follow_relation(Integer sub, Integer obj, Integer flag) {
        User user1 = userDao.getOne(sub);
        User user2 = userDao.getOne(obj);

        UserMongo userMongo1 = user1.getUserMongo();
        UserMongo userMongo2 = user2.getUserMongo();

        List<Integer> followings = userMongo1.getFollowings();
        List<Integer> followers  = userMongo2.getFollowers();

        if (flag == 1) { // follow
            followings.add(obj);
            followers.add(sub);
        }

        else if (flag == -1) {
            followers.remove(sub);
            followings.remove(obj);
        }

        userMongo1.setFollowings(followings);
        userMongo1.setFollowing_num(userMongo1.getFollowing_num() + flag);
        userMongo2.setFollowers(followers);
        userMongo2.setFollower_num(userMongo2.getFollower_num() + flag);
        userDao.update(user1);
        userDao.update(user2);
    }

    @Override
    public String auth(Integer sub, Integer obj, Integer target) {
        User user1 = userDao.getOne(sub);
        User user2 = userDao.getOne(obj);
        Integer subject = user1.getType();
        Integer origin = user2.getType();

        if (Objects.equals(target, origin)) {
            return "target equals";
        }

        // 主语必须是正常的管理员
        if (subject < 1)
            return "sub not admin";

        if (origin == 8) {
            return "obj is boss";
        }

        // 对象是普通用户
        else if (origin == 0 || origin == -8) {
            // 对普通用户的权限升级，需要主语为boss
            if (target != 0 && target != -8) {
                if (subject != 8)
                    return "sub not boss";
            }

            // 对普通用户的封禁/解禁需要100的权限
            else if (subject < 4) {
                return "sub no ban auth";
            }
        }

        else {
            if (subject != 8)
                return "sub not boss";
        }

        user2.setType(target);
        userDao.save(user2);
        return "success";
    }
}
