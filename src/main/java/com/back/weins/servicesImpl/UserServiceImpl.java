package com.back.weins.servicesImpl;

import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.entity.User;
import com.back.weins.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl userDao;

    @Override
    public User getByID(Integer id){
        return userDao.getOne(id);
    }

    @Override
    public User getByName(String name){
        return userDao.getByName(name);
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
        User res = userDao.getOne(user.getUid());
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
}
