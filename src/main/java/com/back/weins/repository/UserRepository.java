package com.back.weins.repository;

import com.back.weins.entity.User;
import com.mongodb.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Nullable
    @Query( value = "select * from user where user.phone = ?1", nativeQuery = true)
    User findByPhone(String phone);

    @Nullable
    @Query( value = "select * from user where user.name = ?1", nativeQuery = true)
    User findByName(String name);

    @Nullable
    @Query(value = "select * from user where user.name like ?1", nativeQuery = true)
    List<User> getByFuzzyName(String name);
}
