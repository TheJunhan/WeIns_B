package com.back.weins.Dao;

import com.back.weins.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getByFuzzyName(String name);

    User getOne(Integer id);

    User getByName(String name);

    User getByPhone(String phone);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(Integer id);
}
