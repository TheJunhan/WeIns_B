package com.back.weins.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "blog")
public class BlogMongo {
    @Id
    @Field
    private Integer id;

    @Field("video")
    private String video;

    @Field("base64")
    private List<String> images;

    @Field("label")
    private List<Label> labels;
}
