package com.back.weins.repository;

import com.back.weins.entity.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserMongo, Integer> {

}
