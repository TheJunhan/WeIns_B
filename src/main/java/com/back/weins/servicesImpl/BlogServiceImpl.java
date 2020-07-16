package com.back.weins.servicesImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.Dao.LabelDao;
import com.back.weins.entity.Label;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private BlogDao blogDao;

//    @Autowired
//    private LabelAndBlogDao labelAndBlogDao;


    @Override
    public void setLabel(String label) {
        System.out.print("运行在service成功！");
        labelDao.setLabel(label);
    }

    @Override
    public Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, String imag, String lab, String username, String useravatar) {
        List<String> image = JSON.parseArray(imag, String.class);
        List<Label> label = JSON.parseArray(lab, Label.class);

        return blogDao.setBlog(uid, type, content, post_day, video, image, label, username, useravatar);
    }

    @Override
    public List<JSONObject> getPublicBlog() {
        return blogDao.getPublicBlog();
    }

    @Override
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid) {
        return blogDao.getBlogsByLabel(lid, uid);
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid) {
        return blogDao.getBlogsLogined(uid);
    }

    @Override
    public boolean setLike(Integer uid, Integer bid) {
        return blogDao.setLike(uid, bid);
    }

    @Override
    public boolean setCollect(Integer uid, Integer bid, boolean flag) {
        return blogDao.setCollect(uid, bid, flag);
    }

    @Override
    public boolean removeLike(Integer uid, Integer bid) {
        return blogDao.removeLike(uid, bid);
    }

    @Override
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day, String username, String useravatar) {
        return blogDao.setReblog(uid, bid, type, content, post_day, username, useravatar);
    }

    @Override
    public boolean removeBlog(Integer uid, Integer bid, Integer type) {
        return blogDao.removeBlog(uid, bid,type);
    }

    @Override
    public boolean setComment(Integer uid, String username, Integer to_uid, String to_username, Integer bid, String content) {
        return blogDao.setComment(uid, username, to_uid, to_username, bid, content);
    }


}
