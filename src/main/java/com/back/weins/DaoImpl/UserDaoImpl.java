package com.back.weins.DaoImpl;

import com.back.weins.entity.User;
import com.back.weins.Dao.UserDao;
import com.back.weins.entity.UserMongo;
import com.back.weins.repository.UserMongoRepository;
import com.back.weins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMongoRepository userMongoRepository;

    public User implUser(User user){
        Optional<UserMongo> userMongo = userMongoRepository.findById(user.getId());

        if(userMongo.isPresent())
            user.setUserMongo(userMongo.get());
        else
            user.setUserMongo(null);

        return user;
    }

    @Override
    public User getOne(Integer id) {
        User user = userRepository.getOne(id);

        return (user == null) ? null : implUser(user);
    }

    @Override
    public User getByName(String name) {
        User user = userRepository.findByName(name);

        return (user == null) ? null : implUser(user);
    }

    @Override
    public User getByPhone(String phone) {
        User user = userRepository.findByPhone(phone);

        return (user == null) ? null : implUser(user);
    }

    public List<User> implUsers(List<User> users) {
        List<User> res = new ArrayList<>();
        for (User user : users) {
            res.add(implUser(user));
        }

        return res;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return implUsers(users);
    }

    @Override
    public void save(User user){
        userRepository.save(user);
        UserMongo userMongo = user.getUserMongo();

        // insert new
        if (user.getId() == null) {
            User user1 = userRepository.findByPhone(user.getPhone());
            assert user1 != null;
            userMongo.setId(user1.getId());
        }

        // update
        userMongoRepository.save(userMongo);
    }

    @Override
    public void update(User user){
        save(user);
    }

    @Override
    public void delete(Integer id){
        userRepository.deleteById(id);
        userMongoRepository.deleteById(id);
    }
}
