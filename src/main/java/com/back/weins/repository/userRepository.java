package com.back.weins.repository;

import com.back.weins.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface userRepository extends JpaRepository<user, Integer> {
    @Query( value = "select ?1 not in( select phone from user ) as res", nativeQuery=true)
    Integer check(String p);
}
