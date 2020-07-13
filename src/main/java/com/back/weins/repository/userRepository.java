package com.back.weins.repository;

import com.back.weins.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface userRepository extends JpaRepository<user, Integer> {
    @Query( value = "select ?1 not in ( select phone from user ) as res", nativeQuery=true)
    Integer check(String phone);
    @Query( value = "select * from user where user.phone = ?1", nativeQuery=true)
    user findByPhone(String phone);
}
