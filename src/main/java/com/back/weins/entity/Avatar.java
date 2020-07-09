package com.back.weins.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;

@Document(collection="avatar")
public class Avatar {
    @Id
    @Field("book_id")
    private Integer bookId;

    @Field("img_base64")
    private String imgBase64;

    public Integer getBookId() {
        return bookId;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}
