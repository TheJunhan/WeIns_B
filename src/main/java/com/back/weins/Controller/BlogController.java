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
    public Integer setBlog(Integer uid, Integer type, String content, String post_day, String video,
                           String imag, String label, String username, String useravatar){
        return blogService.setBlog(uid, type, content, post_day, video, imag, label, username, useravatar);

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

    @PostMapping("/like")
    public boolean setLike(Integer uid, Integer bid){
        return blogService.setLike(uid, bid);
    }

    @PostMapping("/collect")
    public boolean setCollect(Integer uid, Integer bid, boolean flag){
        return blogService.setCollect(uid, bid, flag);
    }

    @PostMapping("/removeLike")
    public boolean removeLike(Integer uid, Integer bid){
        return blogService.removeLike(uid, bid);
    }

    @PostMapping("/setReblog")
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day, String username, String useravatar){
        return blogService.setReblog(uid, bid, type, content, post_day, username, useravatar);
    }

    @PostMapping("/removeBlog")
    public boolean removeBlog(Integer uid, Integer bid, Integer type){
        return blogService.removeBlog(uid, bid, type);
    }

    @PostMapping("/setComment")
    public boolean setComment(Integer uid, String username, Integer to_uid, String to_username, Integer bid, String content){
        return blogService.setComment(uid, username, to_uid, to_username, bid, content);
    }
}
