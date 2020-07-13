package com.back.weins.repository;

import com.back.weins.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query( value = "select ?1 not in ( select phone from user ) as res", nativeQuery=true)
    Integer check(String phone);
    @Query( value = "select * from user where user.phone = ?1", nativeQuery=true)
    User findByPhone(String phone);
}
