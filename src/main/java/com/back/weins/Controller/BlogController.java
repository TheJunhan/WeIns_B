package com.back.weins.Controller;


import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.RequestUtils.BlogUtil;
import com.back.weins.Utils.RequestUtils.CommentUtils;
import com.back.weins.Utils.RequestUtils.ReblogUtil;
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

    @PostMapping("/setBlog")
    public Integer setBlog(@RequestBody BlogUtil blogUtil) {
        System.out.println(blogUtil);
        return blogService.setBlog(blogUtil.getUid(), blogUtil.getType(), blogUtil.getContent(),
                blogUtil.getPost_day(), blogUtil.getVideo(), blogUtil.getImag(), blogUtil.getLabel(),
                blogUtil.getUsername());
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
    public boolean setReblog(@RequestBody ReblogUtil reblogUtil){
        System.out.print(reblogUtil);
        return blogService.setReblog(reblogUtil.getUid(), reblogUtil.getBid(), reblogUtil.getType(), reblogUtil.getContent(), reblogUtil.getPost_day(), reblogUtil.getUsername());
    }

    @PostMapping("/removeBlog")
    public boolean removeBlog(Integer uid, Integer bid, Integer type){
        return blogService.removeBlog(uid, bid, type);
    }

    @PostMapping("/setComment")
    public boolean setComment(@RequestBody CommentUtils commentUtils){
        return blogService.setComment(commentUtils.getUid(), commentUtils.getUsername(), commentUtils.getTo_uid(), commentUtils.getTo_username(), commentUtils.getBid(), commentUtils.getContent());
    }
}
