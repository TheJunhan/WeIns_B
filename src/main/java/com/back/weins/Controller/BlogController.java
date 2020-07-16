package com.back.weins.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping(value="/setLabel")
    public void setLabel(@RequestParam("label") String label) {
        blogService.setLabel(label);
    }

    @RequestMapping("/setBlog")
    public Integer setBlog(Integer uid, Integer type, String post_day, String video,
                           String imag, String label, String username, String useravatar){
        return blogService.setBlog(uid, type, post_day, video, imag, label, username, useravatar);

    }

    @GetMapping("/getPublicBlogs")
    public List<JSONObject> getPublicBlogs(){
        return blogService.getPublicBlog();
    }

    @GetMapping("/getBlogsByLabel")
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid){
        return blogService.getBlogsByLabel(lid, uid);
    }

    @GetMapping("/getBlogsLogined")
    public List<JSONObject> getBlogsLogined(Integer uid){
        return blogService.getBlogsLogined(uid);
    }

    @PostMapping("like")
    public boolean setLike(Integer uid, Integer bid){
        return blogService.setLike(uid, bid);
    }

    @PostMapping("collect")
    public boolean setCollect(Integer uid, Integer bid, boolean flag){
        return blogService.setCollect(uid, bid, flag);
    }

    @PostMapping("removeLike")
    public boolean removeLike(Integer uid, Integer bid){
        return blogService.removeLike(uid, bid);
    }

}
