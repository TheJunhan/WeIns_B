package com.back.weins.repository;

import com.back.weins.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AvatarRepository extends MongoRepository<Avatar, Integer> {
    @Query("{'book_id':?0}")
    Avatar findByUid(Integer uid);
}
