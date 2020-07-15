package com.back.weins.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Data
@Document(collection = "blog")
public class BlogMongo {
    @Id
    @Field
    private Integer id;

    @Field("useravatar")
    private String useravatar;

    @Field("video")
    private String video;

    @Field("base64")
    private List<String> images;

    @Field("label")
    private List<Label> labels;

    @Field("comment")
    private List<Comment> comments;
}
