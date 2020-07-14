package com.back.weins.repository;

import com.back.weins.entity.UserMongo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserMongoRepositoryTest {
    @Autowired
    UserMongoRepository userMongoRepository;

    @Test
    void save() {
        List<Integer> followings = new ArrayList<Integer>();
        List<Integer> followers  = new ArrayList<Integer>();
        List<Integer> blogs      = new ArrayList<Integer>();

        followers.add(178);
        followers.add(123);
        followers.add(24);

        followings.add(179);
        followings.add(13);
        followings.add(145);

        blogs.add(41);
        blogs.add(424);
        blogs.add(4342);

        UserMongo userMongo = new UserMongo();
        userMongo.setId(1);
        userMongo.setAvatar("This is a handsome picture");
        userMongo.setFollowers(followers);
        userMongo.setFollowings(followings);
        userMongo.setBlogs(blogs);
        userMongo.setBlog_num(3);
        userMongo.setFollower_num(3);
        userMongo.setFollowing_num(3);

        userMongoRepository.save(userMongo);
    }

    // save passed

    @Test
    void update() {
        List<Integer> followings = new ArrayList<Integer>();
        List<Integer> followers  = new ArrayList<Integer>();
        List<Integer> blogs      = new ArrayList<Integer>();

        followers.add(178);
        followers.add(123);

        followings.add(179);
        followings.add(13);
        followings.add(145);

        blogs.add(41);

        UserMongo userMongo = new UserMongo();
        userMongo.setId(1);
        userMongo.setAvatar("This is a handsome picture");
        userMongo.setFollowers(followers);
        userMongo.setFollowings(followings);
        userMongo.setBlogs(blogs);
        userMongo.setBlog_num(1);
        userMongo.setFollower_num(2);
        userMongo.setFollowing_num(3);

        userMongoRepository.save(userMongo);
    }

    // update passed

    @Test
    void delete() {
        userMongoRepository.deleteById(2);
    }

    // delete passed

    @Test
    void find() {
        System.out.println(userMongoRepository.findById(2));
    }
}
