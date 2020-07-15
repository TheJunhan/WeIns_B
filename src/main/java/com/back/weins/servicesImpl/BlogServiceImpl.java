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
    public Integer setBlog(Integer uid, Integer type, String post_day, String video, String imag, String lab, String username, String useravatar) {
        List<String> image = JSON.parseArray(imag, String.class);
        List<Label> label = JSON.parseArray(lab, Label.class);

        return blogDao.setBlog(uid, type, post_day, video, image, label, username, useravatar);
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
}
