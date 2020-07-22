package com.back.weins.repository;

import com.back.weins.entity.UserMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class UserMongoRepositoryTest {
//    @Autowired
//    private UserMongoRepository userMongoRepository;
//
//    @BeforeEach
//    public void start() {
//        System.out.println("test start");
//    }
//
//    @AfterEach
//    public void end() {
//        System.out.println("test end");
//    }
//
//    @Test
//    void save() {
//        for (int i = 1; i <= 5; ++i) {
//            UserMongo userMongo = new UserMongo();
//            userMongo.setId(i);
//            userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//            userMongoRepository.save(userMongo);
//
//            assertEquals(userMongoRepository.findById(i).get().getAvatar(),
//                    "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        }
//    }
//
//    // save passed
//
//    @Test
//    void update() {
//        List<Integer> followings = new ArrayList<Integer>();
//        List<Integer> followers  = new ArrayList<Integer>();
//
//        followers.add(1);
//        followers.add(3);
//
//        followings.add(1);
//        followings.add(2);
//        followings.add(4);
//
//        UserMongo userMongo = new UserMongo();
//        userMongo.setId(5);
//        userMongo.setAvatar("http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//        userMongo.setFollowers(followers);
//        userMongo.setFollowings(followings);
//        userMongo.setFollower_num(2);
//        userMongo.setFollowing_num(3);
//
//        userMongoRepository.save(userMongo);
//
//        assertEquals(userMongoRepository.findById(5).get().getAvatar(),
//                "http://bpic.588ku.com/element_pic/01/55/09/6357474dbf2409c.jpg");
//    }
//
//    // update passed
//
//    @Test
//    void delete() {
//        userMongoRepository.deleteById(5);
//    }

    // delete passed
}
