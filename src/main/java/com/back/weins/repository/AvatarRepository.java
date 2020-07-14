package com.back.weins.repository;

import com.back.weins.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AvatarRepository extends MongoRepository<Avatar, Integer> {

}
