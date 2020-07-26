package com.back.weins.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BlogService {
    void setLabel(String label);
    Integer setBlog(Integer uid, Integer type, String content, String post_day, String video,
                           String imag, String label);
    List<JSONObject> getPublicBlog();
    List<JSONObject> getBlogsByLabel(Integer lid, Integer uid);
    List<JSONObject> getBlogsLogined(Integer uid);
    List<JSONObject> getBlogsById(Integer uid, Integer to_see_uid);
    boolean removeComment(Integer uid, Integer cid, Integer type);
    boolean setLike(Integer uid, Integer bid);

    boolean setCollect(Integer uid, Integer bid, boolean flag);

    boolean removeLike(Integer uid, Integer bid);
    boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day);

    boolean removeBlog(Integer uid, Integer bid, Integer type);

    boolean setComment(Integer uid, String username, Integer to_uid, String to_username, Integer bid, String content);
}
