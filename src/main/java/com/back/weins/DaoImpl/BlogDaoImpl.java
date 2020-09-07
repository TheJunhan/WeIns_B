package com.back.weins.DaoImpl;

import com.alibaba.fastjson.JSONObject;
import com.back.weins.Dao.BlogDao;
import com.back.weins.Dao.LabelAndBlogDao;
import com.back.weins.entity.*;
import com.back.weins.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    LabelRepository labelRepository;

    private String findAvatar(Integer uid) {
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        return (userMongo == null) ? "default" : userMongo.getAvatar();
    }
    private String findUsername(Integer uid) {
        User user = userRepository.findById(uid).orElse(null);
        return (user == null) ? "no such user" : user.getName();
    }
    private BlogMongo findBlog(Integer bid) {
        return blogMongoRepository.findById(bid).orElse(null);
    }

    private String findReblogUsername(Integer uid){
        User user = userRepository.findById(uid).orElse(null);
        return (user == null) ? "no such user" : user.getName();
    }

    private Map<Integer, Integer> setInterestMap(Map<Integer, Integer> map, List<Label> labels, Integer weight) {
        for (Label label : labels) {
            Integer id = label.getId();
            if (map.putIfAbsent(id, weight) != null) map.replace(id, map.get(id) + weight);
        }
        return map;
    }

    private JSONObject implComment(Comment comment) {
        JSONObject jsonObject = new JSONObject();

        User user1 = userRepository.findById(comment.getUid()).orElse(null);
        User user2 = userRepository.findById(comment.getTo_uid()).orElse(null);
        UserMongo userMongo1 = userMongoRepository.findById(comment.getUid()).orElse(null);
        UserMongo userMongo2 = userMongoRepository.findById(comment.getTo_uid()).orElse(null);

        assert user1 != null;
        assert user2 != null;
        assert userMongo1 != null;
        assert userMongo2 != null;

        jsonObject.put("cid", comment.getCid());
        jsonObject.put("uid", comment.getUid());
        jsonObject.put("bid", comment.getBid());
        jsonObject.put("username", user1.getName());
        jsonObject.put("to_uid", comment.getTo_uid());
        jsonObject.put("to_username", user2.getName());
        jsonObject.put("content", comment.getContent());
        jsonObject.put("post_time", comment.getPost_time());
        jsonObject.put("avatar", userMongo1.getAvatar());
        jsonObject.put("to_avatar", userMongo2.getAvatar());
        jsonObject.put("to_cid", comment.getTo_cid());
        jsonObject.put("root_cid", comment.getRoot_cid());

        return jsonObject;
    }

    public List<JSONObject> findAllComments(Integer bid){

        List<Comment> comments = commentRepository.findByBid(bid);
        List<JSONObject> res = new ArrayList<>();

        for (Comment comment : comments) {
            if(comment.getIs_del() == 1) continue;
            res.add(implComment(comment));
        }

        return res;
    }

    @Override
    public void TestUtilFunctions() {
        System.out.println(findAvatar(1));
        System.out.println(findUsername(1));
        System.out.println(findReblogUsername(1));
        System.out.println(findAllComments(1));
        System.out.println(findBlog(1));
    }

    @Override
    public Integer setBlog(Integer uid, Integer type, String content,
                           String post_day, String video,
                           List<String> imag, List<Label> lab) {
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

        blogMongoRepository.save(blogMongo);
        for (Label label : lab)
            labelAndBlogDao.setLAB(label.getId(), blog.getId());
        return blog.getId();
    }

    @Override
    @Cacheable(value="blogs")
    public List<JSONObject> getPublicBlog() {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();

        for (Blog blog : blogs) {
            if (blog.getType() != 3 && blog.getType() != 7) continue;
            if (blog.getIs_del() == 1) continue;
            JSONObject tmp = new JSONObject();
            tmp.put("blog", blog);
            tmp.put("blogMongo", findBlog(blog.getId()));

            if (blog.getReblog_id() != -1) {
                Blog blogtmp = blogRepository.findById(blog.getReblog_id()).orElse(null);
                if (blogtmp.getIs_del() == 1) {
                    tmp.put("reblog", "del");
                    tmp.put("reblogMongo", "del");
                    tmp.put("reblogUserName", "del");
                }
                tmp.put("reblog", blogtmp);
                tmp.put("reblogMongo", blogMongoRepository.findById(blog.getReblog_id()));
                tmp.put("reblogUserName", findReblogUsername(blogtmp.getUid()));
            } else {
                tmp.put("reblog", "null");
                tmp.put("reblogMongo", "null");
                tmp.put("reblogUserName", "null");
            }

            tmp.put("userAvatar", findAvatar(blog.getUid()));
            tmp.put("userName", findUsername(blog.getUid()));
            tmp.put("comments", findAllComments(blog.getId()));

            res.add(tmp);
        }
        return res;
    }

    @Override
    @Cacheable(value="blogs_page")
    public List<JSONObject> getPublicBlog_page(Integer index, Integer num) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        Integer tool = 1;
        Integer counter = 0;
        Integer total = blogRepository.getTotal();
        while(true) {
            List<Blog> blogs = blogRepository.findPage(total - index - tool * num, num);
            if(blogs.size() == 0) break;

            boolean tool1 = false;
            for(int i = blogs.size() - 1; i >= 0; --i) {
                counter++;
                if(blogs.get(i).getType()!=3 && blogs.get(i).getType()!=7) continue;
                if(blogs.get(i).getIs_del() == 1) continue;
                JSONObject tmp = new JSONObject();
                tmp.put("blog", blogs.get(i));
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
                if(res.size() >= num) {
                    tool1 = true;
                    break;
                }
            }
            if(tool1) break;
            tool++;
        }
        JSONObject next_index = new JSONObject();
        next_index.put("next_index", index+counter);
        res.add(next_index);
        return res;
    }

    @Override
    public List<JSONObject> getBlogsByLabel(Integer lid, Integer uid, UserMongo Test) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Integer> lab = labelAndBlogRepository.findByLid(lid);
        UserMongo tmp = (Test != null) ? Test : userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for (Integer integer : lab) {
            Blog blog = blogRepository.findById(integer).orElse(null);
            boolean flag = false;
            if (blog.getType() == 7 || blog.getType() == 3) flag = true;
            else if (blog.getType() == 4 || blog.getType() == 0) {
                if (Objects.equals(blog.getUid(), uid)) flag = true;
            } else if (blog.getType() == 5 || blog.getType() == 1) {
                if (following.contains(blog.getUid())) flag = true;
                else if (Objects.equals(blog.getUid(), uid)) flag = true;
            }
            if (!flag) continue;
            if (blog.getIs_del() == 1) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blog);
            BlogMongo blogMongo = blogMongoRepository.findById(integer).orElse(null);
            jsonObject.put("blogMongo", blogMongo);
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
            jsonObject.put("userAvatar", findAvatar(blog.getUid()));
            jsonObject.put("userName", findUsername(blog.getUid()));
            jsonObject.put("comments", findAllComments(blog.getId()));
            res.add(jsonObject);
        }

        return res;
    }

    @Override
    public List<JSONObject> getBlogsByLabel_page(Integer lid, Integer uid, Integer index, Integer num) {
        List<JSONObject> res = new ArrayList<>();
        List<Integer> label_blog = labelAndBlogRepository.findByLid(lid);
        User user = userRepository.findById(uid).orElse(null);
        if(user == null) return null;
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return null;
        List<Integer> followings = userMongo.getFollowings();

        //record用来寻找起始点，counter记录本次终点
        int counter = 0, record = 0;
        if(label_blog == null) return null;
        while(label_blog.get(record) < index) record++;
        for(int i = record; i < label_blog.size(); ++i) {
            counter = label_blog.get(i);
            Blog blog = blogRepository.findById(counter).orElse(null);
            //判断是否应该返回给用户
            boolean flag = false;
            if(blog.getType() == 7 || blog.getType() == 3) flag = true;
            else if(blog.getType() == 4 || blog.getType() == 0) {
                if(blog.getUid() == uid) flag = true;
            }
            else if(blog.getType() == 5 || blog.getType() == 1){
                if(followings.contains(blog.getUid())) flag = true;
                else if(blog.getUid() == uid) flag = true;
            }
            if(!flag) continue;
            if(blog.getIs_del() == 1) continue;
            //开始返回
            res.add(create_json(blog));
            if(res.size() >= num) break;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("next_index", counter);
        res.add(jsonObject);
        return res;
    }

    @Override
    public List<JSONObject> getBlogsLogined(Integer uid, UserMongo Test) {
        List<JSONObject> res = new ArrayList<JSONObject>();
        List<Blog> blogs = blogRepository.findAll();

        UserMongo tmp = (Test != null) ? Test : userMongoRepository.findById(uid).orElse(null);

        List<Integer> following = tmp.getFollowings();
        for (Blog blog : blogs) {
            if (blog.getType() == 0 || blog.getType() == 4) {
                if (!Objects.equals(blog.getUid(), uid)) continue;
            } else if (blog.getType() == 1 || blog.getType() == 5) {
                if (!following.contains(blog.getUid()) && blog.getUid() != uid) continue;
            }

            if (blog.getIs_del() == 1) continue;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("blog", blog);

            jsonObject.put("blogMongo", findBlog(blog.getId()));
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
            jsonObject.put("userAvatar", findAvatar(blog.getUid()));
            jsonObject.put("userName", findUsername(blog.getUid()));
            jsonObject.put("comments", findAllComments(blog.getId()));
            res.add(jsonObject);
        }
        return res;
    }

    @Override
    public List<JSONObject> getBlogsLogined_page(Integer uid, Integer index, Integer num) {
        List<JSONObject> res = new ArrayList<>();
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return null;
        List<Integer> followings = userMongo.getFollowings();
        Integer counter = 0;
        Integer tool = 1;
        Integer total = blogRepository.getTotal();
        while(true) {
            List<Blog> blogs = blogRepository.findPage(total - index - tool * num, num);
            assert blogs != null;
            if(blogs.size() == 0) break;
            for(int i = blogs.size() - 1; i >= 0; --i) {
                counter++;
                //判断是否能返回
                if (blogs.get(i).getType() == 0 || blogs.get(i).getType() == 4)
                    if(!Objects.equals(blogs.get(i).getUid(), uid)) continue;

                else if(blogs.get(i).getType() == 1 || blogs.get(i).getType() == 5){
                    if(!followings.contains(blogs.get(i).getUid()) && !Objects.equals(blogs.get(i).getUid(), uid))
                        continue;
                }
                //能
                res.add(create_json(blogs.get(i)));
                if (res.size() >= num) break;
            }
            if (res.size() >= num) break;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("next_index", index + counter);
        res.add(jsonObject);
        return res;
    }


    @Override
    @Cacheable(value="blogs")
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

        for (Integer value : blogs) {
            Blog blog = blogRepository.findById(value).orElse(null);
            if (blog.getIs_del() == 1) continue;
            //判断这条说说该不该给他看
            Integer integer = user1.getType();
            if (blog.getType() == 0 || blog.getType() == 4)
                if (uid != to_see_uid && integer != 1 && integer != 2 && integer != 4 && integer != 8) continue;
            if (blog.getType() == 1 || blog.getType() == 5)
                if (!fans.contains(uid) &&
                        !Objects.equals(uid, to_see_uid) &&
                        integer != 1 &&
                        integer != 2 &&
                        integer != 4 &&
                        integer != 8) continue;

            JSONObject jsonObject = new JSONObject();

            BlogMongo blogMongo = blogMongoRepository.findById(value).orElse(null);

            jsonObject.put("blog", blog);
            jsonObject.put("blogMongo", blogMongo);

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
            jsonObject.put("userAvatar", userMongo.getAvatar());
            jsonObject.put("userName", user.getName());
            jsonObject.put("comments", findAllComments(value));
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    @Override
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean removeComment(Integer uid, Integer cid, Integer type) {
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if (userMongo == null) return false;
        List<Integer> comments = userMongo.getComments();
        if(!comments.contains(cid) && type != 8 && type != 1) return false;
        Comment comment = commentRepository.findById(cid).orElse(null);
        comment.setIs_del(1);
        commentRepository.saveAndFlush(comment);
        List<Comment> comments1 = commentRepository.findByBid(comment.getBid());
        Integer number = 1;
        for(Comment comment1 : comments1) {
            if(Objects.equals(comment1.getTo_cid(), cid) || Objects.equals(comment1.getRoot_cid(), cid)) {
                comment1.setIs_del(1);
                commentRepository.saveAndFlush(comment1);
                number = number + 1;
            }
        }
        Blog blog = blogRepository.findById(comment.getBid()).orElse(null);
        blog.setCom_number(blog.getCom_number() - number);
        blogRepository.saveAndFlush(blog);

        return true;
    }

    @Override
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean setLike(Integer uid, Integer bid) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        blog.setLike(blog.getLike() + 1);
        BlogMongo blogMongo = blogMongoRepository.findById(blog.getId()).orElse(null);

        if(blogMongo == null) return false;
        List<Integer> list = blogMongo.getWho_like();
        //获取label为了添加用户画像
        List<Label> labels = blogMongo.getLabels();
        if(list.contains(uid)) return false;
        list.add(uid);
        blogMongo.setWho_like(list);
        blogRepository.saveAndFlush(blog);
        blogMongoRepository.deleteById(blog.getId());
        blogMongoRepository.save(blogMongo);
        //对点赞者的操作
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        Map<Integer, Integer> map = userMongo.getInterests();
        //改变画像
        userMongo.setInterests(setInterestMap(map, labels, 1));
        List<Integer> setlike_blog = userMongo.getLike_blog();
        setlike_blog.add(bid);
        userMongo.setLike_blog(setlike_blog);
        userMongoRepository.deleteById(uid);
        userMongoRepository.save(userMongo);
        return true;
    }

    @Override
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean setCollect(Integer uid, Integer bid, boolean flag) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if (blog == null) return false;
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        //添加收藏用户的收藏品
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
        List<Integer> tmplist = userMongo.getCollect_blog();

        if(flag) {
            if(tmplist.contains(bid)) return false;
            tmplist.add(bid);
            //设置画像
            Map<Integer, Integer> map = userMongo.getInterests();
            if(blogMongo == null) return false;
            List<Label> labels = blogMongo.getLabels();
            userMongo.setInterests(setInterestMap(map, labels, 2));
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
    @CacheEvict(value={"blogs", "blogs_page"})
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
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean setReblog(Integer uid, Integer bid, Integer type, String content, String post_day) {
        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if(userMongo == null) return false;
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

        BlogMongo blogMongo = new BlogMongo();
        blogMongo.setId(blog.getId());
        blogMongo.setContent(content);

        BlogMongo tmp = blogMongoRepository.findById(bid).orElse(null);
        blogMongo.setLabels(tmp.getLabels());
        List<Integer> list = tmp.getWho_reblog();
        list.add(uid);
        blogMongoRepository.deleteById(bid);
        blogMongoRepository.save(tmp);
        blogMongoRepository.save(blogMongo);

        //对转发者的操作
        List<Label> labels = blogMongo.getLabels();
        Map<Integer, Integer> map = userMongo.getInterests();
        List<Integer> userBlogs = userMongo.getBlogs();
        //画像
        userMongo.setInterests(setInterestMap(map, labels, 2));
        userBlogs.add(blog.getId());
        //博客
        userMongo.setBlogs(userBlogs);
        //博客数
        userMongo.setBlog_num(userMongo.getBlog_num() + 1);
        userMongoRepository.deleteById(userMongo.getId());
        userMongoRepository.save(userMongo);

        return true;
    }

    @Override
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean removeBlog(Integer uid, Integer bid, Integer type) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        if(!blog.getUid().equals(uid) && type != 2 && type != 8) return false;

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
    @CacheEvict(value={"blogs", "blogs_page"})
    public boolean setComment(Integer uid, Integer to_uid,
                              Integer bid, String content,
                              String post_time, Integer to_cid, Integer root_cid) {
        Blog blog = blogRepository.findById(bid).orElse(null);
        if(blog == null) return false;
        blog.setCom_number(blog.getCom_number() + 1);
        blogRepository.saveAndFlush(blog);

        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        if(blogMongo == null) return false;
        List<Integer> comments = blogMongo.getComments();
        Comment tmp = new Comment(uid, to_uid, bid, content, post_time, to_cid, root_cid);
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
    @CacheEvict(value={"blogs", "blogs_page"})
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
    @Cacheable("blogs")
    public JSONObject getSingleBlog(Integer bid) {
        JSONObject jsonObject = new JSONObject();
        Blog blog = blogRepository.findById(bid).orElse(null);
        BlogMongo blogMongo = blogMongoRepository.findById(bid).orElse(null);
        if (blogMongo == null) return null;

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

    @Override
    public List<JSONObject> recommend(Integer uid, Integer index, Integer num) {
        List<JSONObject> rec_res = new ArrayList<>();

        UserMongo userMongo = userMongoRepository.findById(uid).orElse(null);
        if (userMongo == null) return null;

        Map<Integer, Integer> interest = userMongo.getInterests();
        List<Map.Entry<Integer, Integer>> list_interest = new ArrayList<>(interest.entrySet());

        //去掉删除的标签
        for(int i = 0; i < list_interest.size(); ++i) {
            Label labeltmp = labelRepository.findById(list_interest.get(i).getKey()).orElse(null);
            assert labeltmp != null;
            if(labeltmp.getFlag() == 1) {
                list_interest.remove(i);
                i--;
            }
        }
        List<Integer> followings = userMongo.getFollowings();

        // 对Mongo用户的操作, 最喜欢的三个&关注的人
        // 冒泡排序，得到最喜欢的前三个
        for(int i = 0; i < 3; ++i) {
            for(int j = list_interest.size() -1; j > 0; --j) {
                if(list_interest.get(j).getValue() > list_interest.get(j - 1).getValue()) {
                    Map.Entry<Integer, Integer> tmp = list_interest.get(j);
                    list_interest.set(j, list_interest.get(j - 1));
                    list_interest.set(j - 1, tmp);
                }
            }
        }

        //赋值给most_interest
        List<Integer> most_interest = new ArrayList<>();
        for(int i = 0; i < 3 && i < list_interest.size(); ++i) {
            most_interest.add(list_interest.get(i).getKey());
        }

        // 开始挑选推荐，目前是用最简单的推荐，即前三个喜欢的标签必定会被推荐和关注的人必会被推荐
        Integer tool = 1;
        Integer counter = 0;
        Integer total = blogRepository.getTotal();

        while (true) {
            List<Blog> blogs = blogRepository.findPage(total - index - num * tool, num);

            if(blogs.size() == 0) break;
            tool++;

            for(int i = blogs.size() - 1; i >= 0; --i) {
                boolean judge = false;
                counter++;
                //是否是关注的人
                Blog tmp = blogs.get(i);
                if(followings.contains(tmp.getUid())) judge = true;
                //是否是喜欢的标签
                BlogMongo blogMongo = blogMongoRepository.findById(tmp.getId()).orElse(null);
                assert blogMongo != null;

                List<Label> labels_tmp = blogMongo.getLabels();
                List<Integer> labels_id_tmp = new ArrayList<>();
                //将labellist转换为labelid
                for (Label label : labels_tmp) labels_id_tmp.add(label.getId());

                for(int j = 0; j < 3 && j < most_interest.size(); ++j) {
                    if(labels_id_tmp.contains(most_interest.get(j))) {
                        judge = true;
                        break;
                    }
                }
                //judge=true说明该博客要么是关注的人、要么是喜欢的标签
                if(!judge) continue;

                //推荐
                //检查权限
                if(blogs.get(i).getType() == 0 || blogs.get(i).getType() == 4){
                    if(!Objects.equals(blogs.get(i).getUid(), uid)) continue;
                }
                else if(blogs.get(i).getType() == 1 || blogs.get(i).getType() == 5){
                    if(!followings.contains(blogs.get(i).getUid()) && blogs.get(i).getUid() != uid) continue;
                }
                //生成结果
                JSONObject jsonObject = new JSONObject(create_json(blogs.get(i)));
                rec_res.add(jsonObject);
                if(rec_res.size() >= num) break;
            }
            if(rec_res.size() >= num) break;
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("next_index", index + counter);
        rec_res.add(jsonObject1);
        return rec_res;
    }

    JSONObject create_json(Blog blog) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("blog", blog);

        jsonObject.put("blogMongo", findBlog(blog.getId()));
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
        return jsonObject;
    }

    @Override
    @Cacheable("blogs")
    public List<JSONObject> recommend_notLogin(Integer index, Integer num) {
        List<JSONObject> rec_res = new ArrayList<>();
        Integer counter = 0, tool = 1;
        Integer total = blogRepository.getTotal();

        while(true) { //每页至少推荐一个
            int least = 0;
            //拿到一页
            List<Blog> blogList = blogRepository.findPage(total - index - tool * num, num);

            assert blogList != null;
            if(blogList.size() == 0) break;
            tool++;
            boolean flag = false;
            for(int  i = blogList.size() - 1; i >= 0; --i) {
                counter++;
                //判断是否公开可见
                if(blogList.get(i).getType() != 3 && blogList.get(i).getType()!= 7) continue;
                //判断是否热门，该推荐？
                if(blogList.get(i).getLike() >= 1 || blogList.get(i).getColl_number() >= 1 || blogList.get(i).getReblog() >= 1)
                {
                    least++;
                    rec_res.add(create_json(blogList.get(i)));
                    if(rec_res.size() >= num) {
                        flag = true;
                        break;
                    }
                }
                else if(i == blogList.size() - 1 && least == 0) {
                    rec_res.add(create_json(blogList.get(i)));
                    if(rec_res.size() >= num) {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag) break;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("next_index", index + counter);
        rec_res.add(jsonObject);
        return rec_res;
    }
}
