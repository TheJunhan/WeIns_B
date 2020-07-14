package com.back.weins.servicesImpl;

import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
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
    public User getByPhone(String phone) {
        return userDao.getByPhone(phone);
    }

    @Override
    public List<User> getAll(){
        return userDao.getAll();
    }

    @Override
    public String save(User user){
        if (userDao.getByName(user.getName()) != null)
            return "error";
        userDao.save(user);
        return "success";
    }

    @Override
    public String update(User user){
        User res = userDao.getOne(user.getId());
        if (!Objects.equals(user.getName(), res.getName())) {
            if (userDao.getByName(user.getName()) != null)
                return "error";
        }
        userDao.save(user);
        return "success";
    }

    @Override
    public void delete(Integer id){
        userDao.delete(id);
    }

    @Override
    public String register(User user) {
        if (userDao.getByPhone(user.getPhone()) != null) {
            return "phone error";
        }

        if (userDao.getByName(user.getName()) != null) {
            return "name error";
        }

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
    public User login(String phone, String password) {
        User user1 = userDao.getByPhone(phone);

        if (user1 == null) {
            return userMask(-1);
        }

        else if (!Objects.equals(user1.getPassword(), password)) {
            return userMask(-2);
        }

        user1.setPassword(null);

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
}
