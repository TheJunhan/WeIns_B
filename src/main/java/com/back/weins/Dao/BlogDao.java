package com.back.weins.Dao;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Label;


import java.util.List;

public interface BlogDao {
    JSONObject testBlog(Integer bid);

    Integer setBlog(Integer uid, Integer type, String content, String post_day, String video, List<String> imag,
                    List<Label> label, String username);
    List<JSONObject> getPublicBlog();

    List<JSONObject> getBlogsByLabel(Integer lib, Integer uid);

    List<JSONObject> getBlogsLogined(Integer uid);

    List<JSONObject> getBlogsById(Integer uid);

    boolean removeComment(Integer uid, Integer cid, Integer type);
    boolean setLike(Integer uid, Integer bid);
    boolean setCollect(Integer uid, Integer bid, boolean flag);

    boolean removeLike(Integer uid, Integer bid);
    boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day, String username);

    boolean removeBlog(Integer uid, Integer bid, Integer type);
    boolean setComment(Integer uid, String username, Integer to_uid, String to_username, Integer bid, String content);
}
