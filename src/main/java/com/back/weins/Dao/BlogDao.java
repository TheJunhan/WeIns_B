package com.back.weins.Dao;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Label;
import com.back.weins.entity.UserMongo;

import java.util.List;

public interface BlogDao {
    void TestUtilFunctions();
    Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, List<String> imag, List<Label> label);
    List<JSONObject> getPublicBlog();
    List<JSONObject> getPublicBlog_page(Integer index, Integer num);
    List<JSONObject> getBlogsByLabel(Integer lib, Integer uid, UserMongo Test);
    List<JSONObject> getBlogsByLabel_page(Integer lid, Integer uid, Integer index, Integer num);
    List<JSONObject> getBlogsLogined(Integer uid, UserMongo Test);
    List<JSONObject> getBlogsLogined_page(Integer uid, Integer index, Integer num);
    List<JSONObject> getBlogsById(Integer uid, Integer to_see_uid);
    boolean removeComment(Integer uid, Integer cid, Integer type);
    boolean setLike(Integer uid, Integer bid);
    boolean setCollect(Integer uid, Integer bid, boolean flag);
    boolean removeLike(Integer uid, Integer bid);
    boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day);
    boolean removeBlog(Integer uid, Integer bid, Integer type);
    boolean setComment(Integer uid, Integer to_uid, Integer bid, String content, String post_time, Integer to_cid, Integer root_cid);
    boolean changeBlog(Integer uid, Integer bid, String content, Integer type);
    JSONObject getSingleBlog(Integer bid);
    List<JSONObject> recommend(Integer uid, Integer index, Integer num);
    List<JSONObject> recommend_notLogin(Integer index, Integer num);
}
