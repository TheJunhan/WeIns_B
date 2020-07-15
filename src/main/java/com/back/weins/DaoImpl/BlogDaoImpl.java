package com.back.weins.DaoImpl;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.entity.*;
import com.back.weins.repository.BlogMongoRepository;
import com.back.weins.repository.BlogRepository;

import com.back.weins.repository.LabelAndBlogRepository;
import com.back.weins.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMongoRepository blogMongoRepository;

    @Autowired
    LabelAndBlogDao labelAndBlogDao;

    @Autowired
    LabelAndBlogRepository labelAndBlogRepository;

    @Autowired
    UserMongoRepository userMongoRepository;

    @Override
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           List<String> imag, List<Label> lab, String username, String useravatar) {
        System.out.print(imag);
        System.out.print(lab);

        Blog blog = new Blog();
        blog.setUid(uid);
        blog.setType(type);
        blog.setPost_day(post_day);

        blog.setUsername(username);
        blogRepository.save(blog);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setImages(imag);
        blogMongo.setVideo(video);
        blogMongo.setLabels(lab);

        blogMongo.setUseravatar(useravatar);
        blogMongoRepository.save(blogMongo);
        for (Label label : lab) {
            labelAndBlogDao.setLAB(label.getId(), blog.getId());
        }
        return blog.getId();
    }

    @Override
    public List<JSONObject> getPublicBlog() {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();
        List<BlogMongo> blogMongos = blogMongoRepository.findAll();
        for(int i = 0; i < blogs.size(); ++i){
            if(blogs.get(i).getType()!=3 && blogs.get(i).getType()!=7) continue;
            JSONObject tmp = new JSONObject();
            tmp.put("blog", blogs.get(i));
            tmp.put("blogMongo", blogMongos.get(i));

            res.add(tmp);
        }
        return res;
    }

    @Override
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Integer> lab = labelAndBlogRepository.findByLid(lid);
        UserMongo tmp = userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for(int i = 0; i < lab.size(); ++i){
            Blog blog = blogRepository.findById(lab.get(i)).orElse(null);
            boolean flag = false;
            if(blog.getType() == 7 || blog.getType() == 3) flag = true;
            else if(blog.getType() == 4 || blog.getType() == 0) {
                if(blog.getUid() == uid) flag = true;
            }
            else if(blog.getType() == 5 || blog.getType() == 1){
                if(following.contains(blog.getUid())) flag = true;
            }
            if(!flag) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blog);
            BlogMongo blogMongo = blogMongoRepository.findById(lab.get(i)).orElse(null);
            jsonObject.put("blogMongo", blogMongo);
            res.add(jsonObject);
        }

        return res;
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();
        List<BlogMongo> blogMongos = blogMongoRepository.findAll();
        UserMongo tmp = userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for(int i = 0; i < blogs.size(); ++i){
            if(blogs.get(i).getType() == 0 || blogs.get(i).getType() == 4){
                if(blogs.get(i).getUid() != uid) continue;
            }
            else if(blogs.get(i).getType() == 1 || blogs.get(i).getType() == 5){
                if(!following.contains(blogs.get(i).getUid())) continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blogs.get(i));
            jsonObject.put("blogMongo", blogMongos.get(i));

            res.add(jsonObject);
        }
        return res;
    }

}
