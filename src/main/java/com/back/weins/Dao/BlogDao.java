package com.back.weins.Dao;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Label;


import java.util.List;

public interface BlogDao {

    Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, List<String> imag,
                    List<Label> label, String username, String useravatar);
    List<JSONObject> getPublicBlog();

    List<JSONObject> getBlogsByLabel(Integer lib, Integer uid);

    List<JSONObject> getBlogsLogined(Integer uid);

    boolean setLike(Integer uid, Integer bid);
    boolean setCollect(Integer uid, Integer bid, boolean flag);

    boolean removeLike(Integer uid, Integer bid);
    boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day, String username, String useravatar);

    boolean removeBlog(Integer uid, Integer bid, Integer type);
    boolean setComment(Integer uid, String username, Integer to_uid, String to_username, Integer bid, String content);
}
