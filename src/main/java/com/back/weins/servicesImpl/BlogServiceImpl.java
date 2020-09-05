package com.back.weins.servicesImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.DaoImpl.BlogDaoImpl;
import com.back.weins.DaoImpl.LabelDaoImpl;
import com.back.weins.DaoImpl.UserDaoImpl;
import com.back.weins.entity.Label;
import com.back.weins.entity.User;
import com.back.weins.entity.UserMongo;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private LabelDaoImpl labelDao;

    @Autowired
    private BlogDaoImpl blogDao;

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public String setLabel(String label) {
        Label label1 = new Label();
        label1.setContent(label);
        return labelDao.save(label1);
    }

    @Override
    public Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, String imag, String lab) {
        List<String> image = JSON.parseArray(imag, String.class);
        List<Label> label = JSON.parseArray(lab, Label.class);

        Integer blogId = blogDao.setBlog(uid, type, content, post_day, video, image, label);

        User user = userDao.getOne(uid);
        UserMongo userMongo = user.getUserMongo();
        List<Integer> blogs = userMongo.getBlogs();
        blogs.add(blogId);
        userMongo.setBlogs(blogs);
        userMongo.setBlog_num(userMongo.getBlog_num() + 1);
        user.setUserMongo(userMongo);
        userDao.update(user);

        return blogId;
    }

    @Override
    public List<JSONObject> getPublicBlog() {
        return blogDao.getPublicBlog();
    }

    @Override
    public List<JSONObject> getPublicBlog_page(Integer index, Integer num) {
        return blogDao.getPublicBlog_page(index, num);
    }

    @Override
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid) {
        return blogDao.getBlogsByLabel(lid, uid);
    }

    @Override
    public List<JSONObject> getBlogsByLabel_page(Integer lid, Integer uid, Integer index, Integer num) {
        return blogDao.getBlogsByLabel_page(lid, uid, index, num);
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid) {
        return blogDao.getBlogsLogined(uid);
    }

    @Override
    public List<JSONObject> getBlogsLogined_page(Integer uid, Integer index, Integer num) {
        return blogDao.getBlogsLogined_page(uid, index, num);
    }

    @Override
    public List<JSONObject> getBlogsById(Integer uid, Integer to_see_uid) {
        return blogDao.getBlogsById(uid, to_see_uid);
    }

    @Override
    public boolean removeComment(Integer uid, Integer cid, Integer type) {
        return blogDao.removeComment(uid, cid, type);
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
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day) {
        return blogDao.setReblog(uid, bid, type, content, post_day);
    }

    @Override
    public boolean removeBlog(Integer uid, Integer bid, Integer type) {
        return blogDao.removeBlog(uid, bid,type);
    }

    @Override
    public boolean setComment(Integer uid, Integer to_uid, Integer bid, String content, String post_time, Integer to_cid, Integer root_cid) {
        return blogDao.setComment(uid, to_uid, bid, content, post_time, to_cid, root_cid);
    }

    @Override
    public boolean changeBlog(Integer uid, Integer bid, String content, Integer type) {
        return blogDao.changeBlog(uid, bid, content, type);
    }

    @Override
    public JSONObject getSingleBlog(Integer bid) {
        return blogDao.getSingleBlog(bid);
    }

    @Override
    public List<Label> getLabels() {
        return labelDao.getLabels();
    }

    @Override
    public List<Label> findLabels(String lab) {
        return labelDao.findLabels(lab);
    }

    @Override
    public List<JSONObject> recommend(Integer uid, Integer index, Integer num) {

        return blogDao.recomment(uid, index, num);
    }

    @Override
    public List<JSONObject> recommend_notLogin(Integer index, Integer num) {
        return blogDao.recommend_notLogin(index, num);
    }


}
