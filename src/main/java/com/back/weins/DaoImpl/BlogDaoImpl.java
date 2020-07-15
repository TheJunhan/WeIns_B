package com.back.weins.DaoImpl;

import com.back.weins.Dao.BlogDao;
import com.back.weins.entity.Blog;
import com.back.weins.entity.BlogMongo;
import com.back.weins.entity.Label;
import com.back.weins.repository.BlogMongoRepository;
import com.back.weins.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BlogDaoImpl implements BlogDao {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMongoRepository blogMongoRepository;

    @Override
    public void setBlog(Integer uid, Integer type, String post_day, String video, List<String> imag, List<Label> lab) {
        Blog blog = new Blog();
        blog.setUid(uid);
        blog.setType(type);
        blog.setPost_day(post_day);
        blogRepository.save(blog);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setImages(imag);
        blogMongo.setVideo(video);
        blogMongo.setLabels(lab);
        blogMongoRepository.save(blogMongo);
    }
}
