package com.back.weins.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Data
@Document(collection="follow")
public class UserMongo {
    @Id
    @Field("uid")
    private Integer uid;

    @Field("follower_num")
    private Integer follower_num;

    @Field("following_num")
    private Integer following_num;

    @Field("blog_num")
    private Integer blog_num;

    @Field("followers")
    private List<Integer> followers;

    @Field("following")
    private List<Integer> followings;

    @Field("blog")
    private List<Integer> blogs;
}
