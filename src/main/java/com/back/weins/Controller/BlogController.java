package com.back.weins.Controller;


import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.DaoImpl.BlogDaoImpl;
import com.back.weins.Utils.RequestUtils.BlogUtil;
import com.back.weins.Utils.RequestUtils.ChangeUtil;
import com.back.weins.Utils.RequestUtils.CommentUtils;
import com.back.weins.Utils.RequestUtils.ReblogUtil;
import com.back.weins.entity.Label;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    BlogDaoImpl blogDao;

    @GetMapping(value="/setLabel")
    public void setLabel(@RequestParam("label") String label) {
        blogService.setLabel(label);
    }

    @GetMapping(value="/getLabels")
    public List<Label> getLabels() {
        return blogService.getLabels();
    }

    @GetMapping(value = "/findLabels")
    public List<Label> findLabels(@RequestParam("lab") String lab) {
        return blogService.findLabels(lab);
    }

    @PostMapping("/setBlog")
    public Integer setBlog(@RequestBody BlogUtil blogUtil) {
        System.out.println(blogUtil);
        return blogService.setBlog(blogUtil.getUid(), blogUtil.getType(), blogUtil.getContent(),
                blogUtil.getPost_day(), blogUtil.getVideo(), blogUtil.getImag(), blogUtil.getLabel());
    }

    @GetMapping("/getPublicBlogs")
//    @PreAuthorize("hasRole('from_website')")
    public List<JSONObject> getPublicBlogs(){
        return blogService.getPublicBlog();
    }

    @GetMapping("/page/getPublicBlogs")
    public List<JSONObject> getPublicBlogs_page(@RequestParam("index") Integer index, @RequestParam("num") Integer num) {
        return blogService.getPublicBlog_page(index, num);
    }

    @GetMapping("/getBlogsByLabel")
    public List<JSONObject> getBlogsByLabel(@RequestParam("lid") Integer lid, @RequestParam("uid") Integer uid){
        return blogService.getBlogsByLabel(lid, uid);
    }

    @GetMapping("/getBlogsLogined")
    public List<JSONObject> getBlogsLogined(@RequestParam("uid") Integer uid){
        return blogService.getBlogsLogined(uid);
    }

    @GetMapping("/getBlogsById")
    public List<JSONObject> getBlogsById(@RequestParam("uid") Integer uid, @RequestParam("to_see_uid") Integer to_see_uid) {
        return blogService.getBlogsById(uid, to_see_uid);
    }

    @GetMapping("/like")
    public boolean setLike(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid){
        return blogService.setLike(uid, bid);
    }

    @GetMapping("/collect")
    public boolean setCollect(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid, @RequestParam("flag") boolean flag){
        return blogService.setCollect(uid, bid, flag);
    }

    @GetMapping("/removeLike")
    public boolean removeLike(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid){
        return blogService.removeLike(uid, bid);
    }

    @PostMapping("/setReblog")
    public boolean setReblog(@RequestBody ReblogUtil reblogUtil){
        System.out.print(reblogUtil);
        return blogService.setReblog(reblogUtil.getUid(), reblogUtil.getBid(), reblogUtil.getType(), reblogUtil.getContent(), reblogUtil.getPost_day());
    }

    @GetMapping("/removeBlog")
    public boolean removeBlog(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid, @RequestParam("type") Integer type){
        return blogService.removeBlog(uid, bid, type);
    }

    @PostMapping("/setComment")
    public boolean setComment(@RequestBody CommentUtils commentUtils){
        return blogService.setComment(commentUtils.getUid(), commentUtils.getTo_uid(), commentUtils.getBid(), commentUtils.getContent(), commentUtils.getPost_time(), commentUtils.getTo_cid(), commentUtils.getRoot_cid());
    }
    @PostMapping("/removeComment")
    public boolean removeComment(@RequestParam("uid") Integer uid, @RequestParam("cid") Integer cid, @RequestParam("type") Integer type){
        return blogService.removeComment(uid, cid, type);
    }

    @PostMapping("/changeBlog")
    public boolean changeBlog(@RequestBody ChangeUtil changeUtil) {
        return blogService.changeBlog(changeUtil.getUid(), changeUtil.getBid(), changeUtil.getContent(), changeUtil.getType());
    }

    @GetMapping("/getSingleBlog")
    public JSONObject getSingleBlog(@RequestParam("bid") Integer bid) {
        return blogService.getSingleBlog(bid);
    }

    @GetMapping("/test")
    public List<JSONObject> test(@RequestParam("bid") Integer bid) {
        return blogDao.findAllComments(bid);
    }

    @GetMapping("/page/recommend")
    public List<JSONObject> recommend(@RequestParam("uid") Integer uid, @RequestParam("index") Integer index, @RequestParam("num") Integer num) {
        return null;
    }
}
