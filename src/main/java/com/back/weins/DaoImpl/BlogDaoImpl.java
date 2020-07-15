package com.back.weins.DaoImpl;

import com.back.weins.Dao.BlogDao;
import com.back.weins.entity.*;
import com.back.weins.repository.BlogMongoRepository;
import com.back.weins.repository.BlogRepository;
import com.back.weins.repository.UserMongoRepository;
import com.back.weins.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMongoRepository blogMongoRepository;


    @Override
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           List<String> imag, List<Label> lab, String username, String useravatar) {
        System.out.print(imag);
        System.out.print(lab);
//        //查出uid对应的用户名和头像储存在blog里面，前端浏览时就不用表连接操作了
//        User user = userRepository.findById(uid).orElse(null);
//        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        //常规操作
        Blog blog = new Blog();
        blog.setUid(uid);
        blog.setType(type);
        blog.setPost_day(post_day);
//        blog.setUsername(user.getName());
        blog.setUsername(username);
        blogRepository.save(blog);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setImages(imag);
        blogMongo.setVideo(video);
        blogMongo.setLabels(lab);
//        if(userMongo == null) blogMongo.setUseravatar("default");
//        else blogMongo.setUseravatar(userMongo.getAvatar());
        blogMongo.setUseravatar(useravatar);
        blogMongoRepository.save(blogMongo);
        return blog.getId();
    }
}
