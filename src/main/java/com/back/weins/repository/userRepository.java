package com.back.weins.repository;

import com.back.weins.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Integer> {
}
