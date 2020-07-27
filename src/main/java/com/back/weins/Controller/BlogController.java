package com.back.weins.Controller;


import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.RequestUtils.BlogUtil;
import com.back.weins.Utils.RequestUtils.CommentUtils;
import com.back.weins.Utils.RequestUtils.ReblogUtil;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;




    @GetMapping(value="/setLabel")
//    @PreAuthorize("hasRole('from_website')")
    public void setLabel(@RequestParam("label") String label) {
        blogService.setLabel(label);
    }



    @PostMapping("/setBlog")
//    @PreAuthorize("hasRole('from_website')")
    public Integer setBlog(@RequestBody BlogUtil blogUtil) {
        System.out.println(blogUtil);
        return blogService.setBlog(blogUtil.getUid(), blogUtil.getType(), blogUtil.getContent(),
                blogUtil.getPost_day(), blogUtil.getVideo(), blogUtil.getImag(), blogUtil.getLabel());
    }

    @PostMapping("/getPublicBlogs")
//    @PreAuthorize("hasRole('from_website')")
    public List<JSONObject> getPublicBlogs(){
        return blogService.getPublicBlog();
    }

    @GetMapping("/getBlogsByLabel")
//    @PreAuthorize("hasRole('from_website')")
    public List<JSONObject> getBlogsByLabel(@RequestParam("lid") Integer lid, @RequestParam("uid") Integer uid){
        return blogService.getBlogsByLabel(lid, uid);
    }

    @GetMapping("/getBlogsLogined")
//    @PreAuthorize("hasRole('from_website')")
    public List<JSONObject> getBlogsLogined(@RequestParam("uid") Integer uid){
        return blogService.getBlogsLogined(uid);
    }

    @GetMapping("/getBlogsById")
//    @PreAuthorize("hasRole('from_website')")
    public List<JSONObject> getBlogsById(@RequestParam("uid") Integer uid, @RequestParam("to_see_uid") Integer to_see_uid) {
        return blogService.getBlogsById(uid, to_see_uid);
    }

    @GetMapping("/like")
//    @PreAuthorize("hasRole('from_website')")
    public boolean setLike(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid){
        return blogService.setLike(uid, bid);
    }

    @GetMapping("/collect")
//    @PreAuthorize("hasRole('from_website')")
    public boolean setCollect(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid, @RequestParam("flag") boolean flag){
        return blogService.setCollect(uid, bid, flag);
    }

    @GetMapping("/removeLike")
//    @PreAuthorize("hasRole('from_website')")
    public boolean removeLike(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid){
        return blogService.removeLike(uid, bid);
    }

    @PostMapping("/setReblog")
//    @PreAuthorize("hasRole('from_website')")
    public boolean setReblog(@RequestBody ReblogUtil reblogUtil){
        System.out.print(reblogUtil);
        return blogService.setReblog(reblogUtil.getUid(), reblogUtil.getBid(), reblogUtil.getType(), reblogUtil.getContent(), reblogUtil.getPost_day());
    }

    @GetMapping("/removeBlog")
//    @PreAuthorize("hasRole('from_website')")
    public boolean removeBlog(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid, @RequestParam("type") Integer type){
        return blogService.removeBlog(uid, bid, type);
    }

    @PostMapping("/setComment")
//    @PreAuthorize("hasRole('from_website')")
    public boolean setComment(@RequestBody CommentUtils commentUtils){
        return blogService.setComment(commentUtils.getUid(), commentUtils.getUsername(), commentUtils.getTo_uid(), commentUtils.getTo_username(), commentUtils.getBid(), commentUtils.getContent());
    }
    @GetMapping("/removeComment")
//    @PreAuthorize("hasRole('from_website')")
    public boolean removeComment(@RequestParam("uid") Integer uid, @RequestParam("cid") Integer cid, @RequestParam("type") Integer type){
        return blogService.removeComment(uid, cid, type);
    }

}
