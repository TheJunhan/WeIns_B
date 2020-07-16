package com.back.weins.DaoImpl;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.entity.*;
import com.back.weins.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class BlogDaoImpl implements BlogDao {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogMongoRepository blogMongoRepository;

    @Autowired
    LabelAndBlogDao labelAndBlogDao;

    @Autowired
    LabelAndBlogRepository labelAndBlogRepository;

    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Integer setBlog(Integer uid, Integer type, String content, String post_day, String video,
                           List<String> imag, List<Label> lab, String username, String useravatar) {
        System.out.print(imag);
        System.out.print(lab);

        Blog blog = new Blog();
        blog.setUid(uid);
        blog.setType(type);
        blog.setPost_day(post_day);

        blog.setUsername(username);
        blogRepository.save(blog);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setContent(content);
        blogMongo.setId(blog.getId());
        blogMongo.setImages(imag);
        blogMongo.setVideo(video);
        blogMongo.setLabels(lab);

        blogMongo.setUseravatar(useravatar);
        blogMongoRepository.save(blogMongo);
        for (Label label : lab) {
            labelAndBlogDao.setLAB(label.getId(), blog.getId());
        }
        return blog.getId();
    }

    @Override
    public List<JSONObject> getPublicBlog() {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();
        List<BlogMongo> blogMongos = blogMongoRepository.findAll();
        for(int i = 0; i < blogs.size(); ++i){
            if(blogs.get(i).getType()!=3 && blogs.get(i).getType()!=7) continue;
            if(blogs.get(i).getIs_del() == 1) continue;
            JSONObject tmp = new JSONObject();
            tmp.put("blog", blogs.get(i));
            tmp.put("blogMongo", blogMongos.get(i));

            if(blogs.get(i).getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blogs.get(i).getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    tmp.put("reblog", "del");
                    tmp.put("reblogMongo", "del");
                    continue;
                }
                tmp.put("reblog", blogtmp);
                tmp.put("reblogMongo", blogMongoRepository.findById(blogs.get(i).getReblog_id()));
            }
            else {
                tmp.put("reblog", "null");
                tmp.put("reblogMongo", "null");
            }
            res.add(tmp);
        }
        return res;
    }


    @Override
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Integer> lab = labelAndBlogRepository.findByLid(lid);
        UserMongo tmp = userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for(int i = 0; i < lab.size(); ++i){
            Blog blog = blogRepository.findById(lab.get(i)).orElse(null);
            boolean flag = false;
            if(blog.getType() == 7 || blog.getType() == 3) flag = true;
            else if(blog.getType() == 4 || blog.getType() == 0) {
                if(blog.getUid() == uid) flag = true;
            }
            else if(blog.getType() == 5 || blog.getType() == 1){
                if(following.contains(blog.getUid())) flag = true;
                else if(blog.getUid() == uid) flag = true;
            }
            if(!flag) continue;
            if(blog.getIs_del() == 1) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blog);
            BlogMongo blogMongo = blogMongoRepository.findById(lab.get(i)).orElse(null);
            jsonObject.put("blogMongo", blogMongo);
            if(blog.getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blog.getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    jsonObject.put("reblog", "del");
                    jsonObject.put("reblogMongo", "del");
                    continue;
                }
                jsonObject.put("reblog", blogtmp);
                jsonObject.put("reblogMongo", blogMongoRepository.findById(blog.getReblog_id()));
            }
            else {
                jsonObject.put("reblog", "null");
                jsonObject.put("reblogMongo", "null");
            }
            res.add(jsonObject);
        }

        return res;
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();
        List<BlogMongo> blogMongos = blogMongoRepository.findAll();
        UserMongo tmp = userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for(int i = 0; i < blogs.size(); ++i){
            if(blogs.get(i).getType() == 0 || blogs.get(i).getType() == 4){
                if(blogs.get(i).getUid() != uid) continue;
            }
            else if(blogs.get(i).getType() == 1 || blogs.get(i).getType() == 5){
                if(!following.contains(blogs.get(i).getUid()) && blogs.get(i).getUid() != uid) continue;

            }
            if(blogs.get(i).getIs_del() == 1) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blogs.get(i));
            jsonObject.put("blogMongo", blogMongos.get(i));
            if(blogs.get(i).getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blogs.get(i).getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    jsonObject.put("reblog", "del");
                    jsonObject.put("reblogMongo", "del");
                    continue;
                }
                jsonObject.put("reblog", blogRepository.findById(blogs.get(i).getReblog_id()));
                jsonObject.put("reblogMongo", blogMongoRepository.findById(blogs.get(i).getReblog_id()));
            }
            else {
                jsonObject.put("reblog", "null");
                jsonObject.put("reblogMongo", "null");
            }

            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public boolean setLike(Integer uid, Integer bid) {

        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        blog.setLike(blog.getLike() + 1);
        BlogMongo blogMongo = blogMongoRepository.findById(blog.getId()).orElse(null);
        //assert blogMongo != null;
        if(blogMongo == null) return false;
        List<Integer> list = blogMongo.getWho_like();
        if(list.contains(uid)) return false;
        list.add(uid);
        blogMongo.setWho_like(list);
        blogRepository.saveAndFlush(blog);
        blogMongoRepository.deleteById(blog.getId());
        blogMongoRepository.save(blogMongo);
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        List<Integer> setlike_blog = userMongo.getLike_blog();
        setlike_blog.add(bid);
        userMongo.setLike_blog(setlike_blog);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }

    @Override
    public boolean setCollect(Integer uid, Integer bid, boolean flag) {

        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        List<Integer> tmplist = userMongo.getCollect_blog();

        if(flag) {
            if(tmplist.contains(bid)) return false;
            tmplist.add(bid);

        }
        else{
            if(!tmplist.contains(bid)) return false;
            for(int i = 0; i < tmplist.size(); ++i){
                if(tmplist.get(i).equals(bid)) {
                    tmplist.remove(i);
                    break;
                }
            }
        }
        userMongo.setCollect_blog(tmplist);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);

        Blog blog = blogRepository.findById(bid).orElse(null);
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        List<Integer> who_collectList = blogMongo.getWho_collect();
        if(flag) {
            blog.setColl_number(blog.getCom_number() + 1);
            who_collectList.add(uid);
            blogMongo.setWho_collect(who_collectList);
        }
        else {
            blog.setColl_number(blog.getCom_number() - 1);
            for(int i = 0; i < who_collectList.size(); ++i) if(who_collectList.get(i).equals(uid)) {
                who_collectList.remove(i);
                break;
            }
        }
        blogRepository.saveAndFlush(blog);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(blogMongo);
        return true;
    }

    @Override
    public boolean removeLike(Integer uid, Integer bid) {
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        if(blogMongo == null) return false;
        List<Integer> blogList = blogMongo.getWho_like();
        if(!blogList.contains(uid)) return false;
        for(int i = 0; i < blogList.size(); ++i){
            if(blogList.get(i).equals(uid)) {
                blogList.remove(i);
                break;
            }
        }
        blogMongo.setWho_like(blogList);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(blogMongo);

        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        blog.setLike(blog.getLike() - 1);
        blogRepository.saveAndFlush(blog);

        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        List<Integer> likeBlogList = userMongo.getLike_blog();
        for(int i = 0; i < likeBlogList.size(); ++i) if(likeBlogList.get(i).equals(bid)){
            blogList.remove(i);
            break;
        }
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }

    @Override
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day, String username, String useravatar) {
        Blog blog = new Blog();
        blog.setReblog_id(bid);
        blog.setUid(uid);
        blog.setUsername(username);
        blog.setPost_day(post_day);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setContent(content);
        blogMongo.setUseravatar(useravatar);
        BlogMongo tmp = blogMongoRepository.findById(bid).orElse(null);
        blogMongo.setLabels(tmp.getLabels());
        List<Integer> list = tmp.getWho_reblog();
        list.add(uid);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(tmp);

        blogRepository.save(blog);
        blogMongoRepository.save(blogMongo);
        return true;
    }

    @Override
    public boolean removeBlog(Integer uid, Integer bid, Integer type) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        if(!blog.getUid().equals(uid) && type != 2 && type != 8) return false;
        //BlogMongo blogMongo = blogMongoRepository.findById(blog.getId()).orElse(null);

        blog.setIs_del(1);
        blogRepository.saveAndFlush(blog);
        return true;
    }

    @Override
    public boolean setComment(Integer uid, String username, Integer to_uid,
                              String to_username, Integer bid, String content) {
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        List<Comment> comments = blogMongo.getComments();
        Comment tmp = new Comment(uid, username, to_uid, to_username, content);
        comments.add(tmp);
        blogMongo.setComments(comments);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(blogMongo);

        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        List<Integer> list = userMongo.getComment_blog();
        list.add(bid);
        userMongo.setComment_blog(list);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }
}
