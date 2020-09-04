package com.back.weins.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection="user")
public class UserMongo {
    @Id
    private Integer id;

    @Field("avatar")
    private String avatar;

    @Field("follower_num")
    private Integer follower_num;

    @Field("following_num")
    private Integer following_num;

    @Field("blog_num")
    private Integer blog_num;

    @Field("followers")
    private List<Integer> followers = new ArrayList<Integer>();

    @Field("followings")
    private List<Integer> followings = new ArrayList<Integer>();

    @Field("blogs")
    private List<Integer> blogs = new ArrayList<Integer>();

    @Field("like_blog")
    List<Integer> like_blog = new ArrayList<Integer>();

    @Field("clloect_blog")
    List<Integer> collect_blog = new ArrayList<Integer>();

    @Field("comment_blog")
    List<Integer> comment_blog = new ArrayList<Integer>();

    @Field("comments")
    List<Integer> comments = new ArrayList<Integer>();

    @Field("interests")
    Map<Integer, Integer> interests = new HashMap<Integer, Integer>();

    public UserMongo() {
        this.follower_num = 0;
        this.following_num = 0;
        this.blog_num = 0;
    }

    public UserMongo(UserMongo userMongo) {
        this.avatar = userMongo.getAvatar();

        this.followers = userMongo.getFollowers();
        this.follower_num = userMongo.getFollower_num();

        this.followings = userMongo.getFollowings();
        this.following_num = userMongo.getFollowing_num();

        this.blogs = userMongo.getBlogs();
        this.blog_num = userMongo.getBlog_num();
    }
}
