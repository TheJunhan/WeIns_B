package com.back.weins.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "blog")
public class BlogMongo {
    @Id
    @Field
    private Integer id;

    // 0代表图片 1代表视频
    @Field("type")
    private Integer type;

    @Field("base64")
    private String base64;
}
