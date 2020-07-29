package com.back.weins.DaoImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.entity.*;
import com.back.weins.repository.*;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    private String findAvatar(Integer uid) {
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo==null) return "default";
        return userMongo.getAvatar();
    }
    private String findUsername(Integer uid) {
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) return "该用户不存在";
        return user.getName();
    }
    private BlogMongo findBlog(Integer bid) {
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        return blogMongo;

    }
    private List<JSONObject> findAllComments(Integer bid){
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        List<Integer> comments = blogMongo.getComments();
        List<JSONObject> list = new ArrayList<>();
        for(int i = 0; i < comments.size(); ++i){
            JSONObject jsonObject = new JSONObject();

            Comment comment = commentRepository.findById(comments.get(i)).orElse(null);
            assert comment != null;

            if(comment.getIs_del() == 1)
                continue;

            User user1 = userRepository.findById(comment.getUid()).orElse(null);
            User user2 = userRepository.findById(comment.getTo_uid()).orElse(null);
            UserMongo userMongo1 = userMongoRepository.findById(comment.getUid()).orElse(null);
            UserMongo userMongo2 = userMongoRepository.findById(comment.getTo_uid()).orElse(null);

            jsonObject.put("cid", comment.getCid());
            jsonObject.put("uid", comment.getUid());
            jsonObject.put("username", user1.getName());
            jsonObject.put("to_uid", comment.getTo_uid());
            jsonObject.put("to_username", user2.getName());
            jsonObject.put("content", comment.getContent());
            jsonObject.put("post_time", comment.getPost_time());
            jsonObject.put("avatar", userMongo1.getAvatar());
            jsonObject.put("to_avatar", userMongo2.getAvatar());
            list.add(jsonObject);
        }
        return list;
    }

    private String findReblogUsername(Integer uid){
        User user = userRepository.findById(uid).orElse(null);
        return user.getName();
    }

    @Override
    public JSONObject testBlog(Integer bid) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("blog", blog);
        jsonObject.put("blogMongo", blogMongo);
        return jsonObject;
    }

    @Override
    public Integer setBlog(Integer uid, Integer type, String content, String post_day, String video,
                           List<String> imag, List<Label> lab) {
        System.out.print(imag);
        System.out.print(lab);

        Blog blog = new Blog();
        blog.setUid(uid);
        blog.setType(type);
        blog.setPost_day(post_day);


        blogRepository.save(blog);
        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setContent(content);
        blogMongo.setId(blog.getId());
        blogMongo.setImages(imag);
        blogMongo.setVideo(video);
        blogMongo.setLabels(lab);

        // blogMongo.setUseravatar(useravatar);
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
        //List<BlogMongo> blogMongos = blogMongoRepository.findAll();
        for(int i = 0; i < blogs.size(); ++i) {
            if(blogs.get(i).getType()!=3 && blogs.get(i).getType()!=7) continue;
            if(blogs.get(i).getIs_del() == 1) continue;
            JSONObject tmp = new JSONObject();
            tmp.put("blog", blogs.get(i));
            //tmp.put("blogMongo", blogMongos.get(i));
            tmp.put("blogMongo", findBlog(blogs.get(i).getId()));

            if(blogs.get(i).getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blogs.get(i).getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    tmp.put("reblog", "del");
                    tmp.put("reblogMongo", "del");
                    tmp.put("reblogUserName", "del");
                }
                tmp.put("reblog", blogtmp);
                tmp.put("reblogMongo", blogMongoRepository.findById(blogs.get(i).getReblog_id()));
                tmp.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
            }
            else {
                tmp.put("reblog", "null");
                tmp.put("reblogMongo", "null");
                tmp.put("reblogUserName", "null");
            }

            tmp.put("userAvatar", findAvatar(blogs.get(i).getUid()));
            tmp.put("userName", findUsername(blogs.get(i).getUid()));
            tmp.put("comments", findAllComments(blogs.get(i).getId()));

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
        for(int i = 0; i < lab.size(); ++i) {
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
                    jsonObject.put("reblogUserName", "del");

                }
                jsonObject.put("reblog", blogtmp);
                jsonObject.put("reblogMongo", blogMongoRepository.findById(blog.getReblog_id()));
                jsonObject.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
            }
            else {
                jsonObject.put("reblog", "null");
                jsonObject.put("reblogMongo", "null");
                jsonObject.put("reblogUserName", "null");
            }
            jsonObject.put("userAvatar", findAvatar(blog.getUid()));
            jsonObject.put("userName", findUsername(blog.getUid()));
            jsonObject.put("comments", findAllComments(blog.getId()));
            res.add(jsonObject);
        }

        return res;
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();

        UserMongo tmp = userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for(int i = 0; i < blogs.size(); ++i){
            if(blogs.get(i).getType() == 0 || blogs.get(i).getType() == 4){
                if(!Objects.equals(blogs.get(i).getUid(), uid)) continue;
            }
            else if(blogs.get(i).getType() == 1 || blogs.get(i).getType() == 5){
                if(!following.contains(blogs.get(i).getUid()) && blogs.get(i).getUid() != uid) continue;

            }
            if(blogs.get(i).getIs_del() == 1) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blogs.get(i));
            //jsonObject.put("blogMongo", blogMongos.get(i));
            jsonObject.put("blogMongo", findBlog(blogs.get(i).getId()));
            if(blogs.get(i).getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blogs.get(i).getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    jsonObject.put("reblog", "del");
                    jsonObject.put("reblogMongo", "del");
                    jsonObject.put("reblogUserName", "del");
                }
                jsonObject.put("reblog", blogtmp);
                jsonObject.put("reblogMongo", blogMongoRepository.findById(blogs.get(i).getReblog_id()));
                jsonObject.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
            }
            else {
                jsonObject.put("reblog", "null");
                jsonObject.put("reblogMongo", "null");
                jsonObject.put("reblogUserName", "null");
            }
            jsonObject.put("userAvatar", findAvatar(blogs.get(i).getUid()));
            jsonObject.put("userName", findUsername(blogs.get(i).getUid()));
            jsonObject.put("comments", findAllComments(blogs.get(i).getId()));
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public List<JSONObject> getBlogsById(Integer uid, Integer to_see_uid) {

        UserMongo userMongo = userMongoRepository.findById(to_see_uid).orElse(null);
        if(userMongo == null) return null;
        User user = userRepository.findById(to_see_uid).orElse(null);
        if(user == null) return null;
        User user1 = userRepository.findById(uid).orElse(null);
        if(user1 == null) return null;
        UserMongo userMongo1 = userMongoRepository.findById(uid).orElse(null);
        if(userMongo1 == null) return null;

        List<Integer> blogs = userMongo.getBlogs();
        List<Integer> fans = userMongo1.getFollowings();
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

        for(int i = 0; i < blogs.size(); ++i){
            Blog blog = blogRepository.findById(blogs.get(i)).orElse(null);
            if(blog.getIs_del()==1) continue;
            //判断这条说说该不该给他看
            Integer integer = user1.getType();
            if(blog.getType() == 0 || blog.getType() == 4)
                if(uid != to_see_uid && integer != 1 && integer != 2 && integer != 4 && integer != 8) continue;
            if(blog.getType() == 1 || blog.getType() == 5)
                if(!fans.contains(uid)) continue;


            JSONObject jsonObject = new JSONObject();

            BlogMongo blogMongo = blogMongoRepository.findById(blogs.get(i)).orElse(null);

            jsonObject.put("blog", blog);
            jsonObject.put("blogMongo", blogMongo);

            if(blog.getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blog.getReblog_id()).orElse(null);
                if(blogtmp.getIs_del() == 1) {
                    jsonObject.put("reblog", "del");
                    jsonObject.put("reblogMongo", "del");
                    jsonObject.put("reblogUserName", "del");
                }
                jsonObject.put("reblog", blogtmp);
                jsonObject.put("reblogMongo", blogMongoRepository.findById(blog.getReblog_id()));
                jsonObject.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
            }
            else {
                jsonObject.put("reblog", "null");
                jsonObject.put("reblogMongo", "null");
                jsonObject.put("reblogUserName", "null");
            }
            jsonObject.put("userAvatar", userMongo.getAvatar());
            jsonObject.put("userName", user.getName());
            jsonObject.put("comments", findAllComments(blogs.get(i)));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }


    @Override
    public boolean removeComment(Integer uid, Integer cid, Integer type) {
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        List<Integer> comments = userMongo.getComments();
        if(!comments.contains(cid) && type != 8 && type != 1) return false;
        Comment comment = commentRepository.findById(cid).orElse(null);
        comment.setIs_del(1);
        commentRepository.save(comment);
        return true;
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
        //添加收藏用户的收藏品
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
        //改变blog的收藏者
        Blog blog = blogRepository.findById(bid).orElse(null);
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        List<Integer> who_collectList = blogMongo.getWho_collect();
        if(flag) {
            blog.setColl_number(blog.getColl_number() + 1);
            who_collectList.add(uid);
            blogMongo.setWho_collect(who_collectList);
        }
        else {
            blog.setColl_number(blog.getColl_number() - 1);
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
        userMongo.setLike_blog(blogList);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }

    @Override
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day) {
        Blog blog = new Blog();

        //增加转发数、判断是否是转发
        Blog judgeblog = blogRepository.findById(bid).orElse(null);
        if(judgeblog == null) return false;
        judgeblog.setReblog(judgeblog.getReblog() + 1);
        blogRepository.saveAndFlush(judgeblog);
        if(judgeblog.getReblog_id() == -1) blog.setReblog_id(bid);
        else blog.setReblog_id(judgeblog.getReblog_id());


        blog.setUid(uid);

        blog.setPost_day(post_day);
        blog.setType(type);
        blogRepository.save(blog);
        //System.out.print(blog);

        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setContent(content);

        //System.out.print(blogMongo);
        BlogMongo tmp = blogMongoRepository.findById(bid).orElse(null);
        blogMongo.setLabels(tmp.getLabels());
        List<Integer> list = tmp.getWho_reblog();
        list.add(uid);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(tmp);


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
        Integer tmp = blog.getUid();
        UserMongo userMongo = userMongoRepository.findById(tmp).orElse(null);
        userMongo.setBlog_num(userMongo.getBlog_num() - 1);
        userMongoRepository.deleteById(tmp);
        userMongoRepository.save(userMongo);

        return true;
    }

    @Override
    public boolean setComment(Integer uid, Integer to_uid,
                               Integer bid, String content, String post_time) {

        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        blog.setCom_number(blog.getCom_number() + 1);
        blogRepository.saveAndFlush(blog);

        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        if(blogMongo == null) return false;
        List<Integer> comments = blogMongo.getComments();
//        Comment tmp = new Comment(uid, username, to_uid, to_username, content);
        Comment tmp = new Comment(uid, to_uid, bid, content, post_time);
        commentRepository.save(tmp);
        comments.add(tmp.getCid());

        blogMongo.setComments(comments);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(blogMongo);

        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        List<Integer> list = userMongo.getComment_blog();
        list.add(bid);
        List<Integer> list1 = userMongo.getComments();
        list1.add(tmp.getCid());
        userMongo.setComment_blog(list);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }

    @Override
    public boolean changeBlog(Integer uid, Integer bid, String content, Integer type) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        if(blogMongo == null) return false;
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) return false;
        Integer user_type = user.getType();

        //判断是否可以修改、去除不可修改情况
        if(!blog.getUid().equals(uid) && !blogMongo.getContent().equals(content)) return false;
        if(!blog.getUid().equals(uid) && user_type != 4 && user_type != 2 && user_type != 1 && user_type != 8) return false;

        blog.setType(type);
        blogRepository.saveAndFlush(blog);
        blogMongo.setContent(content);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(blogMongo);
        return true;
    }

    @Override
    public JSONObject getSingleBlog(Integer bid) {
        JSONObject jsonObject = new JSONObject();
        Blog blog = blogRepository.findById(bid).orElse(null);
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);

        jsonObject.put("blog", blog);
        jsonObject.put("blogMongo", blogMongo);
        UserMongo userMongo = userMongoRepository.findById(blog.getUid()).orElse(null);
        jsonObject.put("userAvatar", userMongo.getAvatar());
        User user = userRepository.findById(blog.getUid()).orElse(null);
        jsonObject.put("userName", user.getName());
        jsonObject.put("commetns", findAllComments(bid));

        //转发
        if (blog.getReblog_id() != -1) {
            Blog blogtmp = blogRepository.findById(blog.getReblog_id()).orElse(null);
            if (blogtmp.getIs_del() == 1) {
                jsonObject.put("reblog", "del");
                jsonObject.put("reblogMongo", "del");
                jsonObject.put("reblogUserName", "del");
            }
            jsonObject.put("reblog", blogtmp);
            jsonObject.put("reblogMongo", blogMongoRepository.findById(blog.getReblog_id()));
            jsonObject.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
        } else {
            jsonObject.put("reblog", "null");
            jsonObject.put("reblogMongo", "null");
            jsonObject.put("reblogUserName", "null");
        }
        return jsonObject;
    }

}
