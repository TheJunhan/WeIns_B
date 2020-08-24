package com.back.weins.repository;

import com.back.weins.entity.BlogMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogMongoRepository extends MongoRepository<BlogMongo, Integer> {

}
