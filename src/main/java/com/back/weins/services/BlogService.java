package com.back.weins.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BlogService {
    public void setLabel(String label);
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           String imag, String label, String username, String useravatar);
    List<JSONObject> getPublicBlog();
    List<JSONObject> getBlogsByLabel(Integer lid, Integer uid);
    List<JSONObject> getBlogsLogined(Integer uid);
    boolean setLike(Integer uid, Integer bid);

    boolean setCollect(Integer uid, Integer bid, boolean flag);

    boolean removeLike(Integer uid, Integer bid);
}
