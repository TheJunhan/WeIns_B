package com.back.weins.Controller;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.RequestUtils.BlogUtil;
import com.back.weins.Utils.RequestUtils.ChangeUtil;
import com.back.weins.Utils.RequestUtils.CommentUtils;
import com.back.weins.Utils.RequestUtils.ReblogUtil;
import com.back.weins.entity.Label;
import com.back.weins.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping(value="/setLabel")
    public String setLabel(@RequestParam("label") String label) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info(" set label: "+label);
        return blogService.setLabel(label);
    }

    @GetMapping(value="/getLabels")
    public List<Label> getLabels() {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info(" get labels ");
        return blogService.getLabels();
    }

    @GetMapping(value = "/findFuzzyLabels")
    public List<Label> findLabels(@RequestParam("lab") String lab) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info(" get labels by fuzzyname: "+lab);
        return blogService.findLabels(lab);
    }

    @PostMapping("/setBlog")
    public Integer setBlog(@RequestBody BlogUtil blogUtil) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+blogUtil.getUid()+" set blog : type: "+blogUtil.getType()+" content: "+blogUtil.getContent()+" day: "+
                blogUtil.getPost_day()+" video: "+ blogUtil.getVideo()+" images: "+ blogUtil.getImag()+" label: "+ blogUtil.getLabel());
        return blogService.setBlog(blogUtil.getUid(), blogUtil.getType(), blogUtil.getContent(),
                blogUtil.getPost_day(), blogUtil.getVideo(), blogUtil.getImag(), blogUtil.getLabel(), null);
    }

    @GetMapping("/getPublicBlogs")
    public List<JSONObject> getPublicBlogs(){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info(" getPublicBlogs");
        return blogService.getPublicBlog();
    }

    @GetMapping("/page/getPublicBlogs")
    public List<JSONObject> getPublicBlogs_page(@RequestParam("index") Integer index,
                                                @RequestParam("num") Integer num) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info(" getPublicBlogs by page : index:"+index+" num:"+ num);
        return blogService.getPublicBlog_page(index, num);
    }

    @GetMapping("/getBlogsByLabel")
    public List<JSONObject> getBlogsByLabel(@RequestParam("lid") Integer lid, @RequestParam("uid") Integer uid){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" getBlogs by label : labelid: "+lid);
        return blogService.getBlogsByLabel(lid, uid);
    }

    @GetMapping("/page/getBlogsByLabel")
    public List<JSONObject> getBlogsByLabel_page(@RequestParam("lid") Integer lid,
                                                 @RequestParam("uid") Integer uid,
                                                 @RequestParam("index") Integer index,
                                                 @RequestParam("num") Integer num) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" getBlogs by label : labelid: "+lid+" page  index: "+index+" num: "+ num);
        return blogService.getBlogsByLabel_page(lid, uid, index, num);
    }

    @GetMapping("/getBlogsLogined")
    public List<JSONObject> getBlogsLogined(@RequestParam("uid") Integer uid){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" getBlogslogined ");
        return blogService.getBlogsLogined(uid);
    }

    @GetMapping("/page/getBlogsLogined")
    public List<JSONObject> getBLogsLogined_page(@RequestParam("uid") Integer uid,
                                                 @RequestParam("index") Integer index,
                                                 @RequestParam("num") Integer num) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" getBlogslogined by page: "+" page  index: "+index+" num: "+ num);
        return blogService.getBlogsLogined_page(uid, index, num);
    }

    @GetMapping("/getBlogsById")
    public List<JSONObject> getBlogsById(@RequestParam("uid") Integer uid,
                                         @RequestParam("to_see_uid") Integer to_see_uid) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" getBlogs by id: "+to_see_uid);
        return blogService.getBlogsById(uid, to_see_uid);
    }

    @GetMapping("/like")
    public boolean setLike(@RequestParam("uid") Integer uid,
                           @RequestParam("bid") Integer bid) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" liked Blog -- blog.id: "+bid);
        return blogService.setLike(uid, bid);
    }

    @GetMapping("/removeLike")
    public boolean removeLike(@RequestParam("uid") Integer uid,
                              @RequestParam("bid") Integer bid){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" unliked Blog -- blog.id: "+bid);
        return blogService.removeLike(uid, bid);
    }

    @GetMapping("/collect")
    public boolean setCollect(@RequestParam("uid") Integer uid,
                              @RequestParam("bid") Integer bid,
                              @RequestParam("flag") boolean flag) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        if (flag)
            LOG.info("user : "+uid+" collected Blog -- blog.id: "+bid);

        else
            LOG.info("user : "+uid+" uncollected Blog -- blog.id: "+bid);

        return blogService.setCollect(uid, bid, flag);
    }

    @PostMapping("/setComment")
    public boolean setComment(@RequestBody CommentUtils commentUtils){
        boolean result=blogService.setComment(commentUtils.getUid(), commentUtils.getTo_uid(), commentUtils.getBid(), commentUtils.getContent(), commentUtils.getPost_time(), commentUtils.getTo_cid(), commentUtils.getRoot_cid());

        Logger LOG = LoggerFactory.getLogger(BlogController.class);

        if(result)
            LOG.info("user : "+commentUtils.getUid()+" commented  blog :"+commentUtils.getBid()+" of user :"+commentUtils.getTo_uid()+"  successfully, comment id :"+commentUtils.getTo_cid()+" root comment id :"+commentUtils.getRoot_cid());
        else
            LOG.info("user : "+commentUtils.getUid()+" commented  blog :"+commentUtils.getBid()+" of user :"+commentUtils.getTo_uid()+"  unsuccessfully, comment id :"+commentUtils.getTo_cid()+" root comment id :"+commentUtils.getRoot_cid());


        return result;
    }

    @PostMapping("/removeComment")
    public boolean removeComment(@RequestParam("uid") Integer uid,
                                 @RequestParam("cid") Integer cid,
                                 @RequestParam("type") Integer type){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+uid+" remove comment -- commentid: "+cid+ " type "+type);
        return blogService.removeComment(uid, cid, type);
    }

    @PostMapping("/setReblog")
    public boolean setReblog(@RequestBody ReblogUtil reblogUtil){
        System.out.print(reblogUtil);
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        LOG.info("user : "+reblogUtil.getUid()+" set reblog :"+reblogUtil.getBid()+" type: "+reblogUtil.getType()+" content : "+reblogUtil.getContent()+" day : "+
                reblogUtil.getPost_day());
        return blogService.setReblog(reblogUtil.getUid(), reblogUtil.getBid(), reblogUtil.getType(), reblogUtil.getContent(), reblogUtil.getPost_day());
    }

    @GetMapping("/removeBlog")
    public boolean removeBlog(@RequestParam("uid") Integer uid, @RequestParam("bid") Integer bid, @RequestParam("type") Integer type){
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        boolean result=blogService.removeBlog(uid, bid, type);

        if(result)
            LOG.info("user : "+uid+" removed Blog successfully-- blog.id: "+bid);
        else
            LOG.info("user : "+uid+" failed to remove Blog -- blog.id: "+bid);

        return result;
    }

    @PostMapping("/changeBlog")
    public boolean changeBlog(@RequestBody ChangeUtil changeUtil) {
        Logger LOG = LoggerFactory.getLogger(BlogController.class);
        boolean result=blogService.changeBlog(changeUtil.getUid(), changeUtil.getBid(), changeUtil.getContent(), changeUtil.getType());

        if (result)
            LOG.info("user : "+changeUtil.getUid()+"changed blog "+changeUtil.getBid()+" successfully,content "+changeUtil.getContent()+" type: "+changeUtil.getType());
        else
            LOG.info("user : "+changeUtil.getUid()+"changed blog "+changeUtil.getBid()+" unsuccessfully,content "+changeUtil.getContent()+" type: "+changeUtil.getType());

        return result;
    }

    @GetMapping("/getSingleBlog")
    public JSONObject getSingleBlog(@RequestParam("bid") Integer bid) {
        return blogService.getSingleBlog(bid);
    }

    @GetMapping("/page/recommend")
    public List<JSONObject> recommend(@RequestParam("uid") Integer uid, @RequestParam("index") Integer index, @RequestParam("num") Integer num) {
        return blogService.recommend(uid, index, num);
    }

    @GetMapping("/page/recommendNotLogin")
    public List<JSONObject> recommend_notLogin(@RequestParam("index") Integer index, @RequestParam("num") Integer num) {
        return blogService.recommend_notLogin(index, num);
    }
}
