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
        Integer id = (user.getId() == null) ? 1 : user.getId();
        Optional<UserMongo> mongo = userMongoRepository.findById(id);
        mongo.ifPresent(user::setUserMongo);
        return user;
    }

    @Override
    public List<User> getByFuzzyName(String name) {
        List<User> tmp = userRepository.getByFuzzyName('%'+name+'%');

        return (tmp==null)?null:implUsers(tmp);
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
        UserMongo userMongo = user.getUserMongo();
        User user1 = userRepository.saveAndFlush(user);
        if (user.getId() != null)
            userMongo.setId(user.getId());
        else
            userMongo.setId(user1.getId());
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
