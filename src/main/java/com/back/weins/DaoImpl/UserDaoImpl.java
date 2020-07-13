package com.back.weins.DaoImpl;

import com.back.weins.entity.Avatar;
import com.back.weins.entity.User;
import com.back.weins.Dao.UserDao;
import com.back.weins.repository.AvatarRepository;
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
    private AvatarRepository avatarRepository;

    // 将用户与其头像整合
    public User implUser(User user){
        Integer id = user.getUid();
        Optional<Avatar> avatar = avatarRepository.findById(id);

        if(avatar.isPresent())
            user.setAvatar(avatar.get());
        else
            user.setAvatar(null);

        return user;
    }

    @Override
    public User getOne(Integer id) {
        User user = userRepository.getOne(id);

        return (user == null) ? null : implUser(user);
    }

    @Override
    public User getByName(String name){
        User user = userRepository.findByName(name);

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
        User res = userRepository.findByName(user.getName());
        assert res != null;
        Avatar avatar = new Avatar(res.getUid(), user.getAvatar().getBase64());
        avatarRepository.save(avatar);
    }

    @Override
    public void update(User user){
        save(user);
    }

    @Override
    public void delete(Integer id){
        userRepository.deleteById(id);
        avatarRepository.deleteById(id);
    }
}
