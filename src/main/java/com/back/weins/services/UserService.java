package com.back.weins.services;

import com.back.weins.Utils.RequestUtils.RegisterUtil;
import com.back.weins.entity.User;
import java.util.List;

public interface UserService {
    User getByID(Integer id);

    User getByName(String name);

    User getByPhone(String phone);

    List<User> getByFuzzyName(String name);

    List<User> getAll();

    String save(User user);

    String update(RegisterUtil registerUtil, List<User> Test);

    void delete(Integer id);

    String register(RegisterUtil registerUtil, List<User> Test);

    User login(String phone, String password, User Test);

    void follow_relation(Integer sub, Integer obj, Integer flag, List<User> Test);

    String auth(Integer sub, Integer obj, Integer target, List<User> Test);
}
