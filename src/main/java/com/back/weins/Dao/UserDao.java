package com.back.weins.Dao;

import com.back.weins.entity.User;

import java.util.List;

public interface UserDao {
    User getOne(Integer id);

    User getByName(String name);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(Integer id);

    boolean userBan(Integer id);
}
