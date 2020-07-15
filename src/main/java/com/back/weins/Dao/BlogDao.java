package com.back.weins.Dao;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.entity.Label;


import java.util.List;

public interface BlogDao {

    Integer setBlog(Integer uid, Integer type, String post_day, String video, List<String> imag,
                    List<Label> label, String username, String useravatar);
    List<JSONObject> getPublicBlog();

    List<JSONObject> getBlogsByLabel(Integer lib, Integer uid);

    List<JSONObject> getBlogsLogined(Integer uid);
}
