package com.back.weins.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Document(collection="follow")
public class UserMongo {
    @Id
    @Field("uid")
    private Integer uid;

    @Field("follower_num")
    private Integer follower_num;

    @Field("following_num")
    private Integer following_num;

    @Field("dynamic_num")
    private Integer dynamic_num;

    @Field("followers")
    private List<Integer> followers;

    @Field("following")
    private List<Integer> followings;

    @Field("dynamic")
    private List<Integer> dynamics;

    public Integer getUid() {
        return uid;
    }

    public Integer getFollower_num() {
        return follower_num;
    }

    public Integer getFollowing_num() {
        return following_num;
    }

    public Integer getDynamic_num() {
        return dynamic_num;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public List<Integer> getDynamics() {
        return dynamics;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setFollower_num(Integer follower_num) {
        this.follower_num = follower_num;
    }

    public void setFollowing_num(Integer following_num) {
        this.following_num = following_num;
    }

    public void setDynamic_num(Integer dynamic_num) {
        this.dynamic_num = dynamic_num;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

    public void setDynamics(List<Integer> dynamics) {
        this.dynamics = dynamics;
    }
}
