package com.back.weins.DaoImpl;

import com.back.weins.repository.BlogMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BlogDaoImplTest {
    @Autowired
    BlogDaoImpl blogDao;

    @Autowired
    BlogMongoRepository blogMongoRepository;

    @Test
    public void test(){
        System.out.print(blogDao.testBlog(2));
//        System.out.print(blogMongoRepository.findById(2).orElse(null));
    }
}
