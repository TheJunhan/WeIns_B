package com.back.weins.repository;

import com.back.weins.entity.Avatar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class AvatarRepositoryTest {
    @Autowired
    private AvatarRepository avatarRepository;

    @Test
    void insertOne() {
        Avatar avatar = new Avatar(3, "default");
        avatarRepository.save(avatar);
        System.out.println("insert one test passed!");
    }

    @Test
    void findOne() {
        if (avatarRepository.findById(1).isPresent()) {
            System.out.println(avatarRepository.findById(1).get());
            System.out.println("find one test passed");
        }
    }

    @Test
    void updateOne() {
        String base64 = "this is a handsome picture";
        Avatar avatar = avatarRepository.findById(1).get();
        avatar.setBase64(base64);
        avatarRepository.save(avatar);
        if (Objects.equals(avatarRepository.findById(1).get().getBase64(), base64)) {
            System.out.println("update one test passed!");
        }
    }
}
