package com.back.weins.services;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Label;
import com.back.weins.entity.User;

import java.util.List;

public interface BlogService {
    String setLabel(String label);
    Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, String imag, String label, User Test);

    List<JSONObject> getPublicBlog();
    List<JSONObject> getPublicBlog_page(Integer index, Integer num);
    List<JSONObject> getBlogsByLabel(Integer lid, Integer uid);
    List<JSONObject> getBlogsByLabel_page(Integer lid, Integer uid, Integer index, Integer num);
    List<JSONObject> getBlogsLogined(Integer uid);
    List<JSONObject> getBlogsLogined_page(Integer uid, Integer index, Integer num);
    List<JSONObject> getBlogsById(Integer uid, Integer to_see_uid);


    boolean setLike(Integer uid, Integer bid);
    boolean removeLike(Integer uid, Integer bid);
    boolean setCollect(Integer uid, Integer bid, boolean flag);
    boolean setComment(Integer uid, Integer to_uid, Integer bid, String content, String post_time, Integer to_cid, Integer root_cid);
    boolean removeComment(Integer uid, Integer cid, Integer type);
    boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day);
    boolean changeBlog(Integer uid, Integer bid, String content, Integer type);
    boolean removeBlog(Integer uid, Integer bid, Integer type);

    JSONObject getSingleBlog(Integer bid);

    List<Label> getLabels();
    List<Label> findLabels(String lab);

    List<JSONObject> recommend(Integer uid, Integer index, Integer num);
    List<JSONObject> recommend_notLogin(Integer index, Integer num);
}
